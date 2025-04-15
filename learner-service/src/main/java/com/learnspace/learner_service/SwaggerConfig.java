package com.learnspace.learner_service;

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
        server.setUrl("http://localhost:8080/learnspace/learner"); // ✅ Replace with your service's base URL
        server.setDescription("Learner Service");

        return new OpenAPI()
                .info(new Info()
                        .title("Learner Service API")
                        .version("1.0")
                        .description("API documentation for learner operations"))
                .servers(List.of(server));
    }
}
