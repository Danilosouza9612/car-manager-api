package com.car.manager.api.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Value("${api.config.host}")
    private String host;

    @Bean
    public OpenAPI openAPI(){
        Server server = new Server();
        server.description("Development Host");

        Info information = new Info()
                .version("0.0.1")
                .title("Car Manager API")
                .description("An API to manage cars");

        SecurityScheme scheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");

        Components components = new Components().addSecuritySchemes("Bearer Authentication", scheme);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Authentication");

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .info(information)
                .components(components)
                .servers(List.of(server));
    }
}
