package com.itau.debt.cancel.adapter.in.api.dto;

public class CancelDebtRequestDTO {

    String debtId;

    public String getDebtId() {
        return debtId;
    }

    public void setDebtId(String debtId) {
        this.debtId = debtId;
    }

    public CancelDebtRequestDTO(){}

    public CancelDebtRequestDTO(String debtId){
        this.debtId = debtId;
    }

    public String toString(){
        return String.format("{\"debtId\": \"%s\"}", this.debtId);
    }
}
