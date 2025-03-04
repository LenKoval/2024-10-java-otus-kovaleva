package ru.otus.kovaleva.core.sessionmanager;

import java.sql.Connection;
import java.util.function.Function;

public interface TransactionAction<T> extends Function<Connection, T> {}
