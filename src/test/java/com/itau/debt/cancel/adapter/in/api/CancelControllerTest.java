package com.itau.debt.cancel.adapter.in.api;

import com.itau.debt.cancel.adapter.in.api.controller.CancelController;
import com.itau.debt.cancel.adapter.in.api.dto.CancelDebtRequestDTO;
import com.itau.debt.cancel.port.in.CancelDebtUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CancelController.class)
class CancelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CancelDebtUseCase useCase;

    @Test
    @DisplayName("Deve retornar 200 OK quando o cancelamento for enviado com sucesso")
    void testReturn200() throws Exception {
        String debtId = "12345";

        doNothing().when(useCase).cancel(new CancelDebtRequestDTO(debtId));

        mockMvc.perform(post("/debt/cancel/{debtId}", debtId))
                .andExpect(status().isOk())
                .andExpect(content().string("Debt cancel processed"));

        verify(useCase).cancel(new CancelDebtRequestDTO(debtId));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando o use case lançar IllegalArgumentException")
    void testReturn400() throws Exception {
        String debtId = "invalido";

        // Configura o useCase para lançar IllegalArgumentException
        doThrow(new IllegalArgumentException("Invalid ID")).when(useCase).cancel(new CancelDebtRequestDTO(debtId));

        mockMvc.perform(post("/debt/cancel/{debtId}", debtId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid parameter: Invalid ID"));
    }

    @Test
    @DisplayName("Deve retornar 500 Internal Server Error para exceções não tratadas")
    void testReturn500() throws Exception {
        String debtId = "123";

        // Configura o useCase para lançar RuntimeException
        doThrow(new RuntimeException()).when(useCase).cancel(new CancelDebtRequestDTO(debtId));

        mockMvc.perform(post("/debt/cancel/{debtId}", debtId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error: null"));
    }
}
