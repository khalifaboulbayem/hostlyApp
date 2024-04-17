package com.hostly.hostlyapp.models.dto.response;

import lombok.*;

import java.util.*;

import com.hostly.hostlyapp.enums.AccommodationStatus;
import com.hostly.hostlyapp.enums.AccommodationType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationResponse {

    private UUID id;

    private AccommodationType type;

    private int rooms;

    private Double price;

    private String description;

    private AccommodationStatus status;

    private List<String> fotos;
    // getters and setters
}
