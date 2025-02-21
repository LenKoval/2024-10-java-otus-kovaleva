package ru.otus.kovaleva.dataprocessor;

import ru.otus.kovaleva.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
