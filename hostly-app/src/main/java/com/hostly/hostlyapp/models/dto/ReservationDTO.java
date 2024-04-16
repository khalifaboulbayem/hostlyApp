package com.hostly.hostlyapp.models.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private int id;

    @NotNull(message = "Accommodation is required")
    private AccommodationDTO accommodation;

    @NotNull(message = "User is required")
    private UserDTO user;

    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    // getters and setters
}
