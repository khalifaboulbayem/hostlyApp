package com.hostly.hostlyapp.models.dto;

import lombok.*;

import java.util.UUID;

import com.hostly.hostlyapp.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Size(max = 60, message = "Size must be less than or equal to 60 caracters")
    private String email;

    private Role role;

}
