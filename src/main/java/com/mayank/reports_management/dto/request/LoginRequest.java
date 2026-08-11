package com.mayank.reports_management.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request for user login")
public class LoginRequest {

    private String username;

    private String password;
}