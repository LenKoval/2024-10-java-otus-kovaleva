package ru.otus.kovaleva.atm;

import ru.otus.kovaleva.banknotes.Banknote;
import ru.otus.kovaleva.exceptions.AtmCashOutException;
import ru.otus.kovaleva.storage.BanknoteStorage;
import ru.otus.kovaleva.storage.BanknoteStorageImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AtmImpl implements Atm {

    private final BanknoteStorage banknoteStorage = new BanknoteStorageImpl();

    @Override
    public void load(Banknote banknote) {
        banknoteStorage.put(banknote);
    }

    @Override
    public Collection<Banknote> cashOut(int amount) {
        if (banknoteStorage.amount() < amount) {
            throw new AtmCashOutException("Not enough banknotes to complete the transaction.");
        }

        var nominals = new ArrayList<>(banknoteStorage.getNominals());
        nominals.sort(Collections.reverseOrder());
        List<Banknote> result = new ArrayList<>();
        for (int nominal : nominals) {
            while (amount >= nominal && !banknoteStorage.getBanknoteByNominal(nominal).isEmpty()) {
                Banknote banknote = banknoteStorage.getBanknote(nominal);
                result.add(banknote);
                amount -= nominal;
            }
        }

        if (amount > 0) {
            for (Banknote banknote : result) {
                banknoteStorage.put(banknote);
            }
            throw new AtmCashOutException("Error in dispensing banknotes.");
        }

        return result;
    }

    @Override
    public int getBalance() {
        return banknoteStorage.amount();
    }
}
