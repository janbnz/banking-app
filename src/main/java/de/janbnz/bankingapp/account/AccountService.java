package de.janbnz.bankingapp.account;

import org.springframework.stereotype.Service;

@Service
public final class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

        return this.accountRepository.save(account);
    }

    public Account withdraw(long number, double amount) {
        final Account account = this.getAccountByNumber(number);

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        return this.accountRepository.save(new Account(number, account.getBalance() - amount));
    }

    public double getBalance(long number) {
        return this.getAccountByNumber(number).getBalance();
    }
}