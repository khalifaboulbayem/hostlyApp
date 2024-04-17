package com.hostly.hostlyapp.models.dto.response;

import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

import com.hostly.hostlyapp.enums.PaymentMethod;
import com.hostly.hostlyapp.enums.PaymentStatus;
import com.hostly.hostlyapp.models.dto.ReservationDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PaymentResponse {

    private double amount;

    private LocalDate paymentDate;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    private ReservationDTO reservationDTO;

    // getters and setters
}
