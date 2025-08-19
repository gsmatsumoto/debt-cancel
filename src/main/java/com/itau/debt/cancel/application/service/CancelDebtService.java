package com.itau.debt.cancel.application.service;

import com.itau.debt.cancel.adapter.in.api.dto.CancelDebtRequestDTO;
import com.itau.debt.cancel.port.in.CancelDebtUseCase;
import com.itau.debt.cancel.port.out.EventPublisherPort;
import org.springframework.stereotype.Service;

@Service
public class CancelDebtService implements CancelDebtUseCase {
    private final EventPublisherPort publisher;

    public CancelDebtService(EventPublisherPort publisher) {
        this.publisher = publisher;
    }

    @Override
    public void cancel(CancelDebtRequestDTO debt) {

        System.out.println("Canceling debt ID: " + debt.getDebtId());
        // Atualização do debt id na base de dados

        // Publica no SQS
        System.out.println("Send to SQS: " + debt.getDebtId());
        publisher.publishCancelEvent(debt.toString());

    }
}
