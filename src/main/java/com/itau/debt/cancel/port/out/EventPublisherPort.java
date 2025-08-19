package com.itau.debt.cancel.port.out;

public interface EventPublisherPort {
    void publishCancelEvent(String debt);
}
