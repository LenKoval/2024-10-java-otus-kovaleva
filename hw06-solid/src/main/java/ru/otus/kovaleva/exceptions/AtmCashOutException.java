package ru.otus.kovaleva.exceptions;

public class AtmCashOutException extends RuntimeException {
    public AtmCashOutException(String message) {
        super(message);
    }
}
