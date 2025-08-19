package com.itau.debt.cancel.application.service;

import com.itau.debt.cancel.port.out.EventPublisherPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CancelDeptServiceTest {
    private EventPublisherPort publisher;
    private CancelDebtService service;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        publisher = mock(EventPublisherPort.class);
        service = new CancelDebtService(publisher);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void deveCancelarEPublicarComSucesso() {
        String debitoId = "12345";

        service.cancel(debitoId);

        verify(publisher, atLeastOnce()).publishCancelEvent(debitoId);

        String output = outContent.toString();
        assertTrue(output.contains("Canceling debt ID"));
        assertTrue(output.contains("Send to SQS"));
    }
}
