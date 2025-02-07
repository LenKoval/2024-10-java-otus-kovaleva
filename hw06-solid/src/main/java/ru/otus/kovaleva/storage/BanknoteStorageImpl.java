package ru.otus.kovaleva.storage;

import ru.otus.kovaleva.banknotes.Banknote;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BanknoteStorageImpl implements BanknoteStorage {

    private SortedMap<Integer, BanknoteCell> store = new TreeMap<>(Comparator.comparing(Integer::intValue,
            Comparator.reverseOrder()));

    public BanknoteStorageImpl() {
        for (Banknote banknote: Banknote.values()) {
            store.put(banknote.faceValue(), new BanknoteCell(banknote));
        }
    }

    @Override
    public void put(Banknote banknote) {
        store.get(banknote.faceValue()).add(banknote);
    }

    @Override
    public Collection<Banknote> get(int amount) {
        Collection<Banknote> banknotes = new ArrayList<>();
        if (canGet(amount)) {
            for (BanknoteCell banknoteCell : store.values()) {
                int cnt = Math.min(banknoteCell.count(), amount / banknoteCell.faceValue());
                if (cnt == 0) continue;
                banknotes.addAll(banknoteCell.extract(cnt));
                amount -= cnt * banknoteCell.faceValue();
                if (amount == 0) break;
            }
        }
        return banknotes;
    }

    @Override
    public int getBalance() {
        AtomicInteger balance = new AtomicInteger();
        store.forEach(((faceValue, banknoteCell)
                -> balance.addAndGet(banknoteCell.count() * banknoteCell.faceValue())));
        return balance.get();
    }

    private boolean canGet(int amount) {
        for (BanknoteCell banknoteCell : store.values()) {
            int cnt = Math.min(banknoteCell.count(), amount / banknoteCell.faceValue());
            amount -= cnt * banknoteCell.faceValue();
            if (amount == 0) return true;
        }
        return false;
    }
}
