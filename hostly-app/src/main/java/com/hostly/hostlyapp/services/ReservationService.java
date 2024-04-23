package com.hostly.hostlyapp.services;

import java.util.*;

import com.hostly.hostlyapp.enums.ReservationStatus;
import com.hostly.hostlyapp.models.Reservation;
import com.hostly.hostlyapp.models.dto.ReservationDTO;

public interface ReservationService {
    Collection<ReservationDTO> getAll();

    ReservationDTO getById(UUID id);

    ReservationDTO updateStatus(UUID id, ReservationStatus status);

    ReservationDTO create(ReservationDTO entityDto);

    void delete(UUID id);

    Reservation getReservationByCodeConfirmation(String confirmacionCode);

}
