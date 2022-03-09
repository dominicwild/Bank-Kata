package com.bank_kata_outside_in;

public class BankAccount {

    private TransactionHistory transactionHistory;
    private StatementPrinter statementPrinter;

    public BankAccount(TransactionHistory transactionHistory, StatementPrinter statementPrinter) {
        this.transactionHistory = transactionHistory;
        this.statementPrinter = statementPrinter;
    }

    public void deposit(int amount) {
        notNegative(amount);
        transactionHistory.addTransaction(amount);
    }

    public void withdraw(int amount) {
        notNegative(amount);
        transactionHistory.addTransaction(-amount);
    }

    public void printStatement() {
       statementPrinter.printStatement(transactionHistory);
    }

    private void notNegative(int amount) {
        if (isNegative(amount)) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private boolean isNegative(int amount) {
        return amount < 0;
    }
}
