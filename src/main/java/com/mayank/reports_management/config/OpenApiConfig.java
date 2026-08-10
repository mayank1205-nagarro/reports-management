package com.mayank.reports_management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Report Management API",
                version = "1.0",
                description = "POC for Report Management System"
        )
)
public class OpenApiConfig {
}