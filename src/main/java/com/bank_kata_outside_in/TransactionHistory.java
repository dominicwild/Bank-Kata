package com.bank_kata_outside_in;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransactionHistory {

    private List<Transaction> transactions = new ArrayList<>();
    private Clock clock;

    public TransactionHistory(Clock clock) {
        this.clock = clock;
    }

    public void addTransaction(int transactionAmount) {
        LocalDate today = clock.today();
        transactions.add(new Transaction(transactionAmount, today));
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public TransactionHistory addTransactions(Transaction... transactionsToAdd) {
        transactions.addAll(Arrays.asList(transactionsToAdd));
        return this;
    }

}
