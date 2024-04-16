package com.hostly.hostlyapp.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hostly.hostlyapp.models.Accommodation;
import com.hostly.hostlyapp.models.dto.AccommodationDTO;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {
    @Mapping(target = "id", ignore = true)
    Accommodation accommodationDTOtoAccommodation(AccommodationDTO accommodationDTO);

    AccommodationDTO accommodationToAccommodationDTO(Accommodation accommodation);
}
