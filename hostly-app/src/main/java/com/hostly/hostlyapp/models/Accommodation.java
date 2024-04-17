package com.hostly.hostlyapp.models;

import lombok.*;

import java.util.*;

import com.hostly.hostlyapp.enums.AccommodationStatus;
import com.hostly.hostlyapp.enums.AccommodationType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Accommodation type is required")
    private AccommodationType type;

    @NotNull(message = "Number of rooms is required")
    private int rooms;

    private Double price;

    private String description;

    private AccommodationStatus status;

    @ElementCollection
    @CollectionTable(name = "accommodation_fotos", joinColumns = @JoinColumn(name = "accommodation_id"))
    @Column(columnDefinition = "TEXT")
    private List<String> fotos;

    @OneToMany(mappedBy = "accommodation")
    private List<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
