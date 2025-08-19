package com.itau.debt.cancel.adapter.in.api.controller;

import com.itau.debt.cancel.adapter.in.api.dto.CancelDebtRequestDTO;
import com.itau.debt.cancel.adapter.in.api.dto.CancelDebtResponseDTO;
import com.itau.debt.cancel.port.in.CancelDebtUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/debt")
@Tag(name = "Cancel Debt", description = "Operações de cancelamento de débito")
public class CancelController {

    private final CancelDebtUseCase useCase;

    public CancelController(CancelDebtUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/cancel")
    public ResponseEntity<CancelDebtResponseDTO> cancel(@RequestBody CancelDebtRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getDebtId().isBlank()) {
            throw new IllegalArgumentException("Invalid debt ID");
        }

        useCase.cancel(requestDTO);

        return ResponseEntity.ok(new CancelDebtResponseDTO(200,"Message"));
    }
}
