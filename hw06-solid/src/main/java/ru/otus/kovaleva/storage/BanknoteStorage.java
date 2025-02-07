package ru.otus.kovaleva.storage;

import ru.otus.kovaleva.banknotes.Banknote;

import java.util.Collection;

public interface BanknoteStorage {

    void put(Banknote banknote);

    Collection<Banknote> get(int amount);

    int getBalance();
}
