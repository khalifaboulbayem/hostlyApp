package com.hostly.hostlyapp.services;

import java.util.*;

import com.hostly.hostlyapp.models.dto.AccommodationDTO;
import com.hostly.hostlyapp.models.dto.response.AccommodationResponse;

public interface AccommodationService {

    Collection<AccommodationResponse> getAll();

    AccommodationResponse getById(UUID id);

    AccommodationResponse update(UUID id, AccommodationDTO entityDto);

    AccommodationResponse create(AccommodationDTO entityDto);

    void delete(UUID id);

}
