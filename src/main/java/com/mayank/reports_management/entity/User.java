package com.mayank.reports_management.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Generated user identifier", example = "1")
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "User name", example = "admin")
    private String username;

    @Column(nullable = false)
    @Schema(description = "User password")
    private String password;

    private String role;
}