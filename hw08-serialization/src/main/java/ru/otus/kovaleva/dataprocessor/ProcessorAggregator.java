package ru.otus.kovaleva.dataprocessor;

import ru.otus.kovaleva.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return data.stream()
                .collect(Collectors.groupingBy(
                        Measurement::name,
                        TreeMap::new,
                        Collectors.summingDouble(Measurement::value)
                ));
    }
}
