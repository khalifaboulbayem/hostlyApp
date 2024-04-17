package com.hostly.hostlyapp.models.dto.response;

import lombok.*;

import java.util.*;

import com.hostly.hostlyapp.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;

    private String name;

    private String username;

    private String email;

    private Role role;

    private List<AccommodationResponse> accommodations;

    private List<ReservationResponse> reservations;
}
