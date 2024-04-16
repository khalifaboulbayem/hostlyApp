package com.hostly.hostlyapp.models.dto;

import lombok.*;

import com.hostly.hostlyapp.models.AccommodationType;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {

    private int id;

    @NotNull(message = "Accommodation type is required")
    private AccommodationType type;

    @NotNull(message = "Number of rooms is required")
    private int rooms;

    private Double price;

    // getters and setters
}
