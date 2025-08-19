package com.itau.debt.cancel.port.in;

import com.itau.debt.cancel.adapter.in.api.dto.CancelDebtRequestDTO;

public interface CancelDebtUseCase {
    void cancel(CancelDebtRequestDTO debt);
}
