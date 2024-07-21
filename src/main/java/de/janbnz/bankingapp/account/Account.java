package de.janbnz.bankingapp.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public final class Account {

    @Id
    private long number;

    private double balance;

    public Account(long number, double balance) {
        this();
        this.number = number;
        this.balance = balance;
    }

    public Account() {
    }

    public double getBalance() {
        return balance;
    }

    public long getNumber() {
        return number;
    }
}