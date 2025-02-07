package ru.otus.kovaleva.atm;

import ru.otus.kovaleva.banknotes.Banknote;
import ru.otus.kovaleva.exceptions.AtmCashOutException;
import ru.otus.kovaleva.storage.BanknoteStorage;
import ru.otus.kovaleva.storage.BanknoteStorageImpl;

import java.util.Collection;

public class AtmImpl implements Atm {

    private final BanknoteStorage banknoteStorage = new BanknoteStorageImpl();

    @Override
    public void load(Collection<Banknote> banknotes) {
        banknotes.forEach(banknote -> banknoteStorage.put(banknote));
    }

    @Override
    public Collection<Banknote> cashOut(int amount) {
        if (amount <= 0) throw new AtmCashOutException("The amount of money can't be zero or negative.");
        Collection<Banknote> banknotes = banknoteStorage.get(amount);
        if (banknotes.size() == 0) throw new AtmCashOutException("Can't cash out amount.");
        return banknotes;
    }

    @Override
    public int getBalance() {
        return banknoteStorage.getBalance();
    }
}
