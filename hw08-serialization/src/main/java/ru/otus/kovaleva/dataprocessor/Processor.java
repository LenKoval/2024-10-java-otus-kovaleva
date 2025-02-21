package ru.otus.kovaleva.dataprocessor;

import ru.otus.kovaleva.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
