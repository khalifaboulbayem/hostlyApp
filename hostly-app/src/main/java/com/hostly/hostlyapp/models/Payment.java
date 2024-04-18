package com.hostly.hostlyapp.models;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import com.hostly.hostlyapp.enums.PaymentMethod;
import com.hostly.hostlyapp.enums.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Amount is required")
    private double amount;

    private LocalDate paymentDate;

    private PaymentMethod paymentMethod;

    @NotNull(message = "Status is required")
    private PaymentStatus status;

    @OneToOne
    private Reservation reservation;

}
