package de.janbnz.bankingapp.transaction;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long accountNumber;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;

    private Long targetAccountNumber = null;

    public Transaction() {
    }

    public Transaction(long accountNumber, TransactionType type, double amount, LocalDateTime timestamp) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction(long accountNumber, TransactionType type, double amount, long targetAccountNumber, LocalDateTime timestamp) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.targetAccountNumber = targetAccountNumber;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public long getId() {
        return id;
    }

    public Long getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public TransactionType getType() {
        return type;
    }
}