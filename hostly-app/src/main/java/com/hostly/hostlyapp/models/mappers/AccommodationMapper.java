package com.hostly.hostlyapp.models.mappers;

import org.mapstruct.Mapper;

import com.hostly.hostlyapp.models.Accommodation;
import com.hostly.hostlyapp.models.dto.AccommodationDTO;
import com.hostly.hostlyapp.models.dto.response.AccommodationResponse;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {

    // AccommodationMapper INSTANCE = Mappers.getMapper(AccommodationMapper.class);
    Accommodation accommodationDTOtoAccommodation(AccommodationDTO accommodationDTO);

    AccommodationDTO accommodationToAccommodationDTO(Accommodation accommodation);

    AccommodationResponse accommodationToAccommodationResponse(Accommodation accommodation);

    AccommodationDTO accommodationResponseToAccommodationDTO(AccommodationResponse response);
}
