package com.itau.debt.cancel.adapter.in.api.dto;

public class CancelDebtResponseDTO {
    int status;
    String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CancelDebtResponseDTO(){}

    public CancelDebtResponseDTO(int status, String message){
        this.status = status;
        this.message = message;
    }
}
