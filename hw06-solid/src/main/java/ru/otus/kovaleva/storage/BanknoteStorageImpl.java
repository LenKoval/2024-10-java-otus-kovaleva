package ru.otus.kovaleva.storage;

import ru.otus.kovaleva.banknotes.Banknote;
import ru.otus.kovaleva.exceptions.AtmCashOutException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanknoteStorageImpl implements BanknoteStorage {

    private final Map<Integer, List<Banknote>> cells = new HashMap<>();

    private int amount = 0;

    @Override
    public void put(Banknote banknote) {
        cells.computeIfAbsent(banknote.getFaceValue(), k -> new ArrayList<>()).add(banknote);
        amount = amount + banknote.getFaceValue();
    }

    @Override
    public Banknote getBanknote(int nominal) {
        if (cells.containsKey(nominal)) {
            List<Banknote> banknoteList = cells.get(nominal);
            Banknote banknote = banknoteList.removeFirst();
            amount = amount - banknote.getFaceValue();
            return banknote;
        }
        throw new AtmCashOutException("Not found banknote in storage by nominal:" + nominal);
    }

    @Override
    public List<Banknote> getBanknoteByNominal(int nominal) {
        if (cells.containsKey(nominal)) {
            return List.copyOf(cells.get(nominal));
        }
        return List.of();
    }

    @Override
    public List<Integer> getNominals() {
        return List.copyOf(cells.keySet());
    }

    @Override
    public int amount() {
        return this.amount;
    }
}
