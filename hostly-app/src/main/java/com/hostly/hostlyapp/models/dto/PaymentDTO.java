package com.hostly.hostlyapp.models.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

import com.hostly.hostlyapp.enums.PaymentMethod;
import com.hostly.hostlyapp.enums.PaymentStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private UUID id;

    @NotNull(message = "Reservation is required")
    private UUID reservationId;

    private double amount;

    private LocalDate paymentDate;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    private ReservationDTO reservation;
    // getters and setters
}
