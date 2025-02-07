package ru.otus.kovaleva.storage;

import ru.otus.kovaleva.atm.Atm;
import ru.otus.kovaleva.banknotes.Banknote;
import ru.otus.kovaleva.exceptions.AtmCashOutException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BanknoteCell {

    private final Banknote banknote;

    private List<Banknote> banknotes = new ArrayList<>();

    public BanknoteCell(Banknote banknote) {
        this.banknote = banknote;
    }

    public int faceValue() {
        return banknote.faceValue();
    }

    public void add(Banknote banknote) {
        if (this.banknote.faceValue() != banknote.faceValue()) throw new AtmCashOutException("Not supported face value");
        banknotes.add(banknote);
    }

    public Collection<Banknote> extract(int count) {
        if (count <= 0 || count > banknotes.size()) throw new AtmCashOutException("Incorrect count of banknotes");
        Collection<Banknote> extracted = new ArrayList<>();
        while (count > 0) {
            extracted.add(banknotes.remove(banknotes.size()-1));
            count--;
        }
        return extracted;
    }

    public int count() {
        return banknotes.size();
    }
}
