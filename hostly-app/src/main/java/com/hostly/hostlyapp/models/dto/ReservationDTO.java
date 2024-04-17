package com.hostly.hostlyapp.models.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private UUID id;

    @NotNull(message = "Accommodation is required")
    private UUID accommodationId;

    @NotNull(message = "Accommodation is required")
    private UUID userId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private String confirmationCode;

    // getters and setters
}
