package com.itau.debt.cancel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI cancelamentoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Cancelamento de Débito")
                        .description("API para enviar eventos de cancelamento de débito")
                        .version("v0.0.1"));
    }
}
