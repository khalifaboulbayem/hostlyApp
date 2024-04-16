package com.hostly.hostlyapp.models.dto;

import lombok.*;

import java.util.*;

import com.hostly.hostlyapp.enums.AccommodationStatus;
import com.hostly.hostlyapp.enums.AccommodationType;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {

    private UUID id;

    @NotNull(message = "Accommodation type is required")
    private AccommodationType type;

    @NotNull(message = "Number of rooms is required")
    private int rooms;

    private Double price;

    private String description;

    private AccommodationStatus status;

    private List<String> fotos;
    // getters and setters
}
