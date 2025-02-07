package ru.otus.kovaleva.banknotes;

public enum Banknote {

    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private final int faceValue;

    Banknote(int faceValue) {
        this.faceValue = faceValue;
    }

    public int faceValue() {
        return faceValue;
    }
}
