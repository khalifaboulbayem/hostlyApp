package com.hostly.hostlyapp.models;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Reservation is required")
    @OneToOne
    private Reservation reservation;

    @NotNull(message = "Amount is required")
    private double amount;

    @NotNull(message = "Payment date is required")
    private Date paymentDate;

    // getters and setters
}
