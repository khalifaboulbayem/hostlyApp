package com.hostly.hostlyapp.services;

import java.util.*;

import com.hostly.hostlyapp.models.Reservation;
import com.hostly.hostlyapp.models.dto.ReservationDTO;
import com.hostly.hostlyapp.models.dto.response.ReservationResponse;

public interface ReservationService {
    Collection<ReservationResponse> getAll();

    ReservationResponse getById(UUID id);

    /// ReservationDTO update(UUID id, ReservationDTO entityDto);

    ReservationResponse create(ReservationDTO entityDto);

    void delete(UUID id);

    Reservation getReservationByCodeConfirmation(String confirmacionCode);

}
