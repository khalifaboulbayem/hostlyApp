package com.hostly.hostlyapp.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hostly.hostlyapp.models.Reservation;
import com.hostly.hostlyapp.models.dto.ReservationDTO;

@Mapper(componentModel = "spring", uses = { AccommodationMapper.class, UserMapper.class })
public interface ReservationMapper {
    @Mapping(target = "id", ignore = true)
    Reservation reservationDTOtoReservation(ReservationDTO reservationDTO);

    ReservationDTO reservationToReservationDTO(Reservation reservation);
}
