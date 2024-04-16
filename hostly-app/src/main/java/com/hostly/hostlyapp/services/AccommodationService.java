package com.hostly.hostlyapp.services;

import java.util.*;

import com.hostly.hostlyapp.models.dto.AccommodationDTO;

public interface AccommodationService {

    Collection<AccommodationDTO> getAll();

    AccommodationDTO getById(UUID id);

    AccommodationDTO update(UUID id, AccommodationDTO entityDto);

    AccommodationDTO create(AccommodationDTO entityDto);

    void delete(UUID id);

}
