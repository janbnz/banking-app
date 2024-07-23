package de.janbnz.bankingapp.transaction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionByAccountNumber(long accountNumber) {
        return this.transactionRepository.getByAccountNumber(accountNumber);
    }
}