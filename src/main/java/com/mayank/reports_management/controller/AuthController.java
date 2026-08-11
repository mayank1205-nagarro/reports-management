package com.mayank.reports_management.controller;

import com.mayank.reports_management.dto.request.LoginRequest;
import com.mayank.reports_management.dto.response.LoginResponse;
import com.mayank.reports_management.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
    name = "Auth Management",
    description = "API for user login"
)
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(
        summary = "User Login API",
        description = "Users can login using this API."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful, JWT token returned"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request. Username or password is missing."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password."
            )
    })
    public LoginResponse login(
            @RequestBody LoginRequest request) throws BadRequestException {

        if(request.getUsername() == null || request.getPassword() == null) {
            throw new BadRequestException("Username or password missing");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        String token =
                jwtUtil.generateToken(
                        request.getUsername());

        return new LoginResponse(token);
    }
}