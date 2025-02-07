package ru.otus.kovaleva;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.kovaleva.atm.Atm;
import ru.otus.kovaleva.atm.AtmImpl;
import ru.otus.kovaleva.banknotes.Banknote;
import ru.otus.kovaleva.exceptions.AtmCashOutException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtmImplTest {

    private Atm atm;
    private int initialBalance;
    private Banknote[] banknotesForTest = {Banknote.ONE_HUNDRED,
            Banknote.TWO_HUNDRED,
            Banknote.ONE_THOUSAND,
            Banknote.ONE_HUNDRED,
            Banknote.FIVE_HUNDRED,
            Banknote.ONE_HUNDRED,
            Banknote.TWO_HUNDRED,
            Banknote.ONE_HUNDRED,
            Banknote.ONE_HUNDRED,
            Banknote.FIVE_THOUSAND,
            Banknote.TWO_THOUSAND,
            Banknote.TWO_HUNDRED,
            Banknote.FIVE_THOUSAND,
            Banknote.ONE_THOUSAND};

    @BeforeEach
    void before() {
        atm = new AtmImpl();

        Collection<Banknote> banknotes = new ArrayList<>();
        for (Banknote banknote : this.banknotesForTest) {
            banknotes.add(banknote);
            initialBalance += banknote.faceValue();
        }
        atm.load(banknotes);
    }

    @Test
    void load() {
        List<Banknote> banknotesToLoad = List.of(Banknote.TWO_HUNDRED, Banknote.ONE_HUNDRED);

        atm.load(banknotesToLoad);

        assertEquals(initialBalance + 300, atm.getBalance());
    }

    @Test
    void cashOutMinCorrect() {
        int amount = 100;

        Collection<Banknote> b1 = atm.cashOut(amount);

        assertEquals(1, b1.size());
        assertEquals(amount, (int) b1.stream().map(Banknote::faceValue).reduce(Integer::sum).get());
    }

    @Test
    void cashOutMaxCorrect() {
        int amount  = initialBalance;

        Collection<Banknote> b2 = atm.cashOut(amount);

        assertEquals(banknotesForTest.length, b2.size());
        assertEquals(amount, (int) b2.stream().map(Banknote::faceValue).reduce(Integer::sum).get());
    }

    @Test
    void cashCorrect() {
        int amount = 200;

        Collection<Banknote> b2 = atm.cashOut(amount);

        assertEquals(1, b2.size());
        assertEquals(amount, (int) b2.stream().map(Banknote::faceValue).reduce(Integer::sum).get());
    }

    @Test
    void cashOutNegative() {
        int amount = -1;

        assertThrows(AtmCashOutException.class, () -> atm.cashOut(amount));
    }

    @Test
    void cashIncorrect() {
        int amount = 1;

        assertThrows(AtmCashOutException.class, () -> atm.cashOut(amount));
    }

    @Test
    void getBalance() {
        long balance = atm.getBalance();

        assertEquals(initialBalance, balance);
    }
}
