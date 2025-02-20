package ru.otus.kovaleva.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.kovaleva.processor.service.TimeBasedService;
import ru.otus.kovaleva.model.Message;
import ru.otus.kovaleva.processor.exceptions.TimeBasedException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeBasedExceptionProcessorTest {

    @Mock
    private TimeBasedService timeBasedService;

    private TimeBasedExceptionProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new TimeBasedExceptionProcessor(timeBasedService);
    }

    @Test
    void shouldProcessMessageInOddSecond() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 0, 0, 1);
        when(timeBasedService.getTime()).thenReturn(time);
        Message message = new Message.Builder(1L).build();

        assertDoesNotThrow(() -> processor.process(message));
    }

    @Test
    void shouldThrowExceptionInEvenSecond() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 0, 0, 2);
        when(timeBasedService.getTime()).thenReturn(time);
        Message message = new Message.Builder(1L).build();

        TimeBasedException exception = assertThrows(
                TimeBasedException.class,
                () -> processor.process(message)
        );
        assertEquals("Exception thrown in even second", exception.getMessage());
        verify(timeBasedService).getTime();
    }
}
