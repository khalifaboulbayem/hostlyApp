package com.hostly.hostlyapp.models.dto.response;

import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private UUID id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String confirmationCode;

    private PaymentResponse payment;
    // getters and setters
}
