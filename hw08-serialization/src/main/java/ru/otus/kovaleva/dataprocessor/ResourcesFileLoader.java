package ru.otus.kovaleva.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.kovaleva.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    private final ObjectMapper objectMapper;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found " + fileName);
            }

            return objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Measurement.class));
        } catch (IOException e) {
            throw new FileProcessException("Error while reading a file");
        }
    }
}
