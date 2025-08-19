package com.itau.debt.cancel.adapter.in.api.exception;

import com.itau.debt.cancel.adapter.in.api.dto.CancelDebtResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CancelDebtResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new CancelDebtResponseDTO(400,"Invalid parameter: " + ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CancelDebtResponseDTO> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CancelDebtResponseDTO(500, "Internal server error: " + ex.getMessage()));
    }
}

