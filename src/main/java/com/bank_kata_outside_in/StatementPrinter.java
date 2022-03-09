package com.bank_kata_outside_in;

import static java.util.stream.Collectors.toCollection;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import com.bank_kata_outside_in.external.Console;

public class StatementPrinter {

    private static final DecimalFormat TWO_DECIMAL_PLACES = new DecimalFormat("#.00");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Console console;

    public StatementPrinter(Console console) {
        this.console = console;
    }

    public void printStatement(TransactionHistory transactionHistory) {
        console.printLine("DATE | AMOUNT | BALANCE");

        AtomicInteger runningBalance = new AtomicInteger(0);

        transactionHistory.getTransactions().stream()
                .sorted(inAscendingDateOrder())
                .map(transaction -> {
                    int currentBalance = runningBalance.addAndGet(transaction.getAmount());
                    return statmentLineOf(transaction, currentBalance);
                })
                .collect(toCollection(LinkedList::new))
                .descendingIterator()
                .forEachRemaining(console::printLine);
    }

    private Comparator<? super Transaction> inAscendingDateOrder() {
        return (transaction, toCompare) -> -toCompare.getDate().compareTo(transaction.getDate());
    }

    private String statmentLineOf(Transaction transaction, int runningBalance) {
        String transactionDate = DATE_FORMAT.format(transaction.getDate());
        String transactionAmount = TWO_DECIMAL_PLACES.format(transaction.getAmount());
        String runningBalanceAmount = TWO_DECIMAL_PLACES.format(runningBalance);

        return String.format("%s | %s | %s", transactionDate, transactionAmount, runningBalanceAmount);
    }

}
