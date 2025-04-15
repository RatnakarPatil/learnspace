package com.learnspace.mentor_service;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080/learnspace/mentor"); // ✅ Replace with your service's base URL
        server.setDescription("Mentor Service"); // Update per service

        return new OpenAPI()
                .info(new Info()
                        .title("Mentor Service API")
                        .version("1.0")
                        .description("API documentation for mentor operations"))
                .servers(List.of(server));
    }
}
