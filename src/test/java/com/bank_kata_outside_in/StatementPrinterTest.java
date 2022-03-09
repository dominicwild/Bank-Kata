package com.bank_kata_outside_in;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import com.bank_kata_outside_in.external.Clock;
import com.bank_kata_outside_in.external.Console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatementPrinterTest {

    @Mock
    Console console;

    @Mock
    Clock clock;

    StatementPrinter statementPrinter;

    @BeforeEach
    void setUp() {
        statementPrinter = new StatementPrinter(console);
    }

    @Test
    void printing_statement_prints_statement_header() {
        statementPrinter.printStatement(transactionHistory());

        verify(console).printLine("DATE | AMOUNT | BALANCE");
    }

    @Test
    void printing_statement_prints_transaction_lines() {
        TransactionHistory transactionHistory = transactionHistory().addTransactions(
                new Transaction(1000, LocalDate.of(2018, 1, 31)));

        statementPrinter.printStatement(transactionHistory);

        verify(console).printLine("31/01/2018 | 1000.00 | 1000.00");
    }

    @Test
    void printing_statement_prints_running_balance() {
        TransactionHistory transactionHistory = transactionHistory().addTransactions(
                new Transaction(1000, LocalDate.of(2018, 1, 31)),
                new Transaction(-100, LocalDate.of(2018, 2, 28)),
                new Transaction(500, LocalDate.of(2018, 4, 15)));

        statementPrinter.printStatement(transactionHistory);

        verify(console).printLine("31/01/2018 | 1000.00 | 1000.00");
        verify(console).printLine("28/02/2018 | -100.00 | 900.00");
        verify(console).printLine("15/04/2018 | 500.00 | 1400.00");
    }

    @Test
    void printing_statement_prints_transactions_in_descending_order_by_date() {
        TransactionHistory transactionHistory = transactionHistory().addTransactions(
                new Transaction(-100, LocalDate.of(2018, 2, 28)),
                new Transaction(1000, LocalDate.of(2018, 1, 31)),
                new Transaction(500, LocalDate.of(2018, 4, 15)));

        statementPrinter.printStatement(transactionHistory);

        InOrder inOrder = inOrder(console);
        inOrder.verify(console).printLine("15/04/2018 | 500.00 | 1400.00");
        inOrder.verify(console).printLine("28/02/2018 | -100.00 | 900.00");
        inOrder.verify(console).printLine("31/01/2018 | 1000.00 | 1000.00");
    }

    private TransactionHistory transactionHistory() {
        return new TransactionHistory(clock);
    }

}
