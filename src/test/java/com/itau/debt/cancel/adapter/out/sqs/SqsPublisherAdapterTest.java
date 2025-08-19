package com.itau.debt.cancel.adapter.out.sqs;

import com.itau.debt.cancel.adapter.out.sqs.publisher.SqsPublisherAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SqsPublisherAdapterTest {
    private SqsClient sqsClient;
    private SqsPublisherAdapter adapter;

    private final String queueUrl = "https://sqs.us-east-1.amazonaws.com/012345678910/minha-fila.fifo";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        sqsClient = mock(SqsClient.class);
        adapter = new SqsPublisherAdapter(sqsClient, queueUrl);

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(outContent));
    }

    @BeforeEach
    void restoreSystemOut() {
        outContent.reset();
    }


    @Test
    void testSuccessfulSentSQS() {
        String debtId = "abc123";

        SendMessageResponse response = (SendMessageResponse) SendMessageResponse.builder()
                .messageId("msg-123")
                .sdkHttpResponse(SdkHttpResponse.builder().statusCode(200).build())
                .build();

        when(sqsClient.sendMessage(any(SendMessageRequest.class))).thenReturn(response);

        adapter.publishCancelEvent(debtId);

        String output = outContent.toString();
        assertTrue(output.contains("Message sent successfully"));
    }

    @Test
    void testErrorSentSQS() {
        String debtId = "abc123";

        SendMessageResponse response = (SendMessageResponse) SendMessageResponse.builder()
                .messageId("msg-123")
                .sdkHttpResponse(SdkHttpResponse.builder().statusCode(500).build())
                .build();

        when(sqsClient.sendMessage(any(SendMessageRequest.class))).thenReturn(response);

        adapter.publishCancelEvent(debtId);

        String output = outContent.toString();
        assertTrue(output.contains("Error sending message. Status:"));
    }
}
