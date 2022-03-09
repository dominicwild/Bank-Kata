package com.bank_kata_outside_in;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import com.bank_kata_outside_in.external.Clock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionHistoryTest {

    private static final LocalDate TODAY = LocalDate.of(2020, 1, 31);

    @Mock
    Clock clock;

    TransactionHistory transactionHistory;

    @BeforeEach
    void setUp() {
        transactionHistory = new TransactionHistory(clock);
    }

    @Test
    void transaction_amount_is_stored_in_history() {
        int transactionAmount = 1000;
        transactionHistory.addTransaction(transactionAmount);

        List<Transaction> transactions = transactionHistory.getTransactions();

        assertEquals(1, transactions.size());

        Transaction firstTransaction = transactions.get(0);
        assertEquals(transactionAmount, firstTransaction.getAmount());
    }

    @Test
    void transaction_is_stored_with_current_date() {
        when(clock.today()).thenReturn(TODAY);

        int transactionAmount = 1000;
        transactionHistory.addTransaction(transactionAmount);

        List<Transaction> transactions = transactionHistory.getTransactions();

        assertEquals(1, transactions.size());

        Transaction firstTransaction = transactions.get(0);
        assertEquals(TODAY, firstTransaction.getDate());
    }
}
