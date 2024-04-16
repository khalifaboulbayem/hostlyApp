package com.hostly.hostlyapp.models.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDTO {
    private int id;

    @NotNull(message = "Reservation is required")
    private ReservationDTO reservation;

    private double amount;

    @NotNull(message = "Payment date is required")
    private Date paymentDate;

    // getters and setters
}
