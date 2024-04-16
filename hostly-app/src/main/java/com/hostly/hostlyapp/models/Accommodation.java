package com.hostly.hostlyapp.models;

import lombok.*;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotNull(message = "Accommodation type is required")
    private AccommodationType type;

    @NotNull(message = "Number of rooms is required")
    private int rooms;

    private Double price;

    // getters and setters
}
