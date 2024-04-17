package com.hostly.hostlyapp.models.mappers;

import org.mapstruct.Mapper;

import com.hostly.hostlyapp.models.Reservation;
import com.hostly.hostlyapp.models.dto.ReservationDTO;
import com.hostly.hostlyapp.models.dto.response.ReservationResponse;

@Mapper(componentModel = "spring", uses = { AccommodationMapper.class, UserEntityMapper.class })
public interface ReservationMapper {
    Reservation reservationDTOtoReservation(ReservationDTO reservationDTO);

    Reservation reservationResponsetoReservation(ReservationResponse res);

    ReservationDTO reservationToReservationDTO(Reservation reservation);

    ReservationResponse reservationToReservationResponse(Reservation reservation);

    ReservationDTO reservationResponseToReservationDTO(ReservationResponse res);
}
