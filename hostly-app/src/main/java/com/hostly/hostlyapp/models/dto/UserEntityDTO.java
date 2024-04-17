package com.hostly.hostlyapp.models.dto;

import lombok.*;

import java.util.*;

import com.hostly.hostlyapp.enums.Role;
import com.hostly.hostlyapp.models.dto.AccommodationDTO;
import com.hostly.hostlyapp.models.dto.response.AccommodationResponse;
import com.hostly.hostlyapp.models.dto.response.ReservationResponse;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDTO {
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank
    @Size(min = 0, max = 30)
    private String username;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Size(max = 60, message = "Size must be less than or equal to 60 caracters")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private Role role;

    private List<AccommodationResponse> accommodations;

    private List<ReservationResponse> reservations;
}
