package ru.otus.kovaleva.core.sessionmanager;

public interface TransactionRunner {

    <T> T doInTransaction(TransactionAction<T> action);
}
