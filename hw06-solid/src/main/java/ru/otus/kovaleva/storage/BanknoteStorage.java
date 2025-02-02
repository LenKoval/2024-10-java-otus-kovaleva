package ru.otus.kovaleva.storage;

import ru.otus.kovaleva.banknotes.Banknote;

import java.util.List;

public interface BanknoteStorage {

    void put(Banknote banknote);

    Banknote getBanknote(int nominal);

    List<Banknote> getBanknoteByNominal(int nominal);

    List<Integer> getNominals();

    int amount();
}
