package ru.otus.kovaleva.dataprocessor;

import ru.otus.kovaleva.model.Measurement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return data.stream()
                .collect(Collectors.groupingBy(
                        Measurement::name,
                        LinkedHashMap::new,  // HashMap не гарантирует порядок поэтому использовала LinkedHasMap
                        Collectors.summingDouble(Measurement::value)
                ));
    }
}
