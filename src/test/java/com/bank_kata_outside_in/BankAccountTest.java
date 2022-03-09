package com.bank_kata_outside_in;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankAccountTest {

    @Mock
    TransactionHistory transactionHistory;

    @Mock
    StatementPrinter statementPrinter;

    BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(transactionHistory, statementPrinter);
    }

    @Test
    void deposit_amount_must_be_positive(){
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-1));
    }

    @Test
    void deposit_is_recorded_in_transaction_history() {
        int transactionAmount = 1000;
        account.deposit(transactionAmount);

        verify(transactionHistory).addTransaction(transactionAmount);
    }

    @Test
    void withdraw_amount_must_be_positive(){
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-1));
    }

    @Test
    void withdraw_is_recorded_in_transaction_history() {
        int transactionAmount = 1000;
        account.withdraw(transactionAmount);

        verify(transactionHistory).addTransaction(-transactionAmount);
    }

    @Test
    void print_statement_prints_transaction_history() {
        account.printStatement();

        verify(statementPrinter).printStatement(transactionHistory);
    }
}
