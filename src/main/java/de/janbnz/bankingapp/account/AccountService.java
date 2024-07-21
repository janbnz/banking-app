package de.janbnz.bankingapp.account;

import de.janbnz.bankingapp.transaction.Transaction;
import de.janbnz.bankingapp.transaction.TransactionRepository;
import de.janbnz.bankingapp.transaction.TransactionType;
import de.janbnz.bankingapp.transaction.TransferToSelfException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public final class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account getAccountByNumber(long number) {
        return this.accountRepository.getByNumber(number).orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public Account deposit(long number, double amount) {
        Account account = this.accountRepository.getByNumber(number).orElse(null);

        if (account == null) {
            account = new Account(number, amount);
        } else {
            account = new Account(number, account.getBalance() + amount);
        }

        // Save to transaction history
        final Transaction transaction = new Transaction(number, TransactionType.DEPOSIT, amount, LocalDateTime.now());
        this.transactionRepository.save(transaction);

        return this.accountRepository.save(account);
    }

    public Account withdraw(long number, double amount) {
        final Account account = this.getAccountByNumber(number);

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Save to transaction history
        final Transaction transaction = new Transaction(number, TransactionType.WITHDRAWAL, amount, LocalDateTime.now());
        this.transactionRepository.save(transaction);

        return this.accountRepository.save(new Account(number, account.getBalance() - amount));
    }

    public Account transfer(long from, long to, double amount) {
        if (to == from) {
            throw new TransferToSelfException("You can't transfer money to yourself");
        }

        final Account fromAccount = this.getAccountByNumber(from);
        final Account toAccount = this.getAccountByNumber(to);

        if (fromAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Save to transaction history
        final Transaction transactionFrom = new Transaction(from, TransactionType.TRANSFER_OUTGOING, amount, to, LocalDateTime.now());
        final Transaction transactionTo = new Transaction(to, TransactionType.TRANSFER_INCOMING, amount, from, LocalDateTime.now());
        this.transactionRepository.save(transactionFrom);
        this.transactionRepository.save(transactionTo);

        this.accountRepository.save(new Account(from, fromAccount.getBalance() - amount));
        return this.accountRepository.save(new Account(to, toAccount.getBalance() + amount));
    }
}