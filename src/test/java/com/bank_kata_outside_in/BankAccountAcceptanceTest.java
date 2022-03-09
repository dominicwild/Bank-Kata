package com.bank_kata_outside_in;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankAccountAcceptanceTest {

    @Mock
    Console console;

    @Mock
    Clock clock;

    TransactionHistory transactionHistory; 
    StatementPrinter statementPrinter;

    @BeforeEach
    void setUp() {
        transactionHistory = new TransactionHistory(clock);
        statementPrinter = new StatementPrinter(console);
    }

    @Test
    void statement_shows_bank_account_transaction_history(){
        BankAccount account = new BankAccount(transactionHistory, statementPrinter);

        when(clock.today()).thenReturn(LocalDate.of(2018, 4, 1));
        account.deposit(1000);

        when(clock.today()).thenReturn(LocalDate.of(2018, 4, 2));
        account.withdraw(100);
        
        when(clock.today()).thenReturn(LocalDate.of(2018, 4, 10));
        account.deposit(500);

        account.printStatement();

        verify(console, times(1)).printLine("DATE | AMOUNT | BALANCE");
        verify(console, times(1)).printLine("10/04/2018 | 500.00 | 1400.00");
        verify(console, times(1)).printLine("02/04/2018 | -100.00 | 900.00");
        verify(console, times(1)).printLine("01/04/2018 | 1000.00 | 1000.00");
    }

}
