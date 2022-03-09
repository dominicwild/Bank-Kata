package com.bank_kata_outside_in;

import java.time.LocalDate;

public class Transaction {

    private int transactionAmount;
    private LocalDate date;

    public Transaction(int transactionAmount, LocalDate dateOfTransaction) {
        this.transactionAmount = transactionAmount;
        this.date = dateOfTransaction;
    }

    public int getAmount() {
        return transactionAmount;
    }

    public LocalDate getDate() {
        return date;
    }

}
