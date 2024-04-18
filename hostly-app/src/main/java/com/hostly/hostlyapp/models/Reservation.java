package com.hostly.hostlyapp.models;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

import com.hostly.hostlyapp.enums.ReservationStatus;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Column(name = "confirmation_Code")
    private String confirmationCode;

    @NotNull(message = "Reservation status is required")
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @NotNull(message = "UserEntity is required")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    private Payment payment;
    // getters and setters
}
