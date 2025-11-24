package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Detector de Mutantes (MercadoLibre Challenge)")
                        .version("1.0.0")
                        .description("API REST para analizar secuencias de ADN y determinar si un humano es mutante. Incluye lógica de persistencia y estadísticas.")
                        .contact(new Contact()
                                .name("/GitHub")
                                .url("https://github.com/tu-usuario/repo-mutantes")
                                .email("franreyessegura@gmail.com")));
    }
}