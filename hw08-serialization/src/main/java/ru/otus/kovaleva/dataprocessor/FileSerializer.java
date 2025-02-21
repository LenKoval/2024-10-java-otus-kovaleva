package ru.otus.kovaleva.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    private final ObjectMapper objectMapper;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try {

            if (data == null || data.isEmpty()) {
                throw new FileProcessException("Empty data for serialization");
            }

            Path path = Paths.get(fileName);
            Files.createDirectories(path.getParent());

            try (OutputStream outputStream = Files.newOutputStream(path)) {
                objectMapper.writeValue(outputStream, data);
            }
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
