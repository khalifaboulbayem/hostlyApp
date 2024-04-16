package com.hostly.hostlyapp.services;

import java.util.*;

import com.hostly.hostlyapp.models.Reservation;
import com.hostly.hostlyapp.models.dto.ReservationDTO;

public interface ReservationService {
    Collection<ReservationDTO> getAll();

    ReservationDTO getById(UUID id);

    /// ReservationDTO update(UUID id, ReservationDTO entityDto);

    ReservationDTO create(ReservationDTO entityDto);

    void delete(UUID id);

    Reservation getReservationByCodeConfirmation(String confirmacionCode);

}
