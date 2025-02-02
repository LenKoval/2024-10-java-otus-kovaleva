package ru.otus.kovaleva;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.kovaleva.atm.AtmImpl;
import ru.otus.kovaleva.banknotes.Banknote;
import ru.otus.kovaleva.banknotes.OneHundredBanknote;
import ru.otus.kovaleva.banknotes.FiveHundredBanknote;
import ru.otus.kovaleva.banknotes.OneThousandBanknote;
import ru.otus.kovaleva.banknotes.TwoThousandBanknote;
import ru.otus.kovaleva.exceptions.AtmCashOutException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtmImplTest {

    private AtmImpl atm;

    @BeforeEach
    void setUp() {
        atm = new AtmImpl();
    }

    @Test
    void testLoadBanknote() {
        Banknote banknote = new OneHundredBanknote();
        atm.load(banknote);
        assertEquals(100, atm.getBalance());
    }

    @Test
    void testMultipleLoads() {
        Banknote hundred = new OneHundredBanknote();
        Banknote fiveHundred = new FiveHundredBanknote();

        atm.load(hundred);
        atm.load(fiveHundred);

        assertEquals(600, atm.getBalance());
    }

    @Test
    void testCashOutExactAmount() {
        Banknote hundred = new OneHundredBanknote();
        atm.load(hundred);

        var result = atm.cashOut(100);
        assertEquals(1, result.size());
        assertTrue(result.contains(hundred));
        assertEquals(0, atm.getBalance());
    }

    @Test
    void testCashOutMultipleBanknotes() {
        Banknote hundred = new OneHundredBanknote();
        Banknote oneThousand = new OneThousandBanknote();

        atm.load(hundred);
        atm.load(oneThousand);

        var result = atm.cashOut(1100);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(hundred, oneThousand)));
        assertEquals(0, atm.getBalance());
    }

    @Test
    void testCashOutInsufficientFunds() {
        Banknote twoThousand = new TwoThousandBanknote();
        atm.load(twoThousand);

        assertThrows(AtmCashOutException.class, () -> atm.cashOut(2100));
        assertEquals(2000, atm.getBalance());
    }

    @Test
    void testCashOutInvalidAmount() {
        Banknote hundred = new OneHundredBanknote();
        atm.load(hundred);

        assertThrows(AtmCashOutException.class, () -> atm.cashOut(75));
        assertEquals(100, atm.getBalance());
    }

    @Test
    void testGetBalanceEmpty() {
        assertEquals(0, atm.getBalance());
    }
}
