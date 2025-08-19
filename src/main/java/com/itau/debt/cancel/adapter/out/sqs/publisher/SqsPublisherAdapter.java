package com.itau.debt.cancel.adapter.out.sqs.publisher;

import com.itau.debt.cancel.port.out.EventPublisherPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Component
public class SqsPublisherAdapter implements EventPublisherPort {
    private final SqsClient sqsClient;
    private final String queueUrl;

    public SqsPublisherAdapter(SqsClient sqsClient, @Value("${aws.sqs.queue.url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    @Override
    public void publishCancelEvent(String debt) {
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(debt)
                .build();

        SendMessageResponse response = sqsClient.sendMessage(request);
        if (response.sdkHttpResponse().isSuccessful()) {
            System.out.println("Message sent successfully. ID: " + response.messageId());
        } else {
            System.err.println("Error sending message. Status: " +
                    response.sdkHttpResponse().statusCode());
        }
    }
}
