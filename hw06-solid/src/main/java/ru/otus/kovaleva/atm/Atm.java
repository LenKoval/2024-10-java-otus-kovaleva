package ru.otus.kovaleva.atm;

import ru.otus.kovaleva.banknotes.Banknote;

import java.util.Collection;

public interface Atm {

    void load(Collection<Banknote> banknotes);

    Collection<Banknote> cashOut(int amount);

    int getBalance();
}
