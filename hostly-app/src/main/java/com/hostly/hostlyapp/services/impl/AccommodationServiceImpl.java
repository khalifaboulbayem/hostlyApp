package com.hostly.hostlyapp.services.impl;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostly.hostlyapp.handlers.exceptions.BadRequestException;
import com.hostly.hostlyapp.handlers.exceptions.NotFoundException;
import com.hostly.hostlyapp.models.Accommodation;
import com.hostly.hostlyapp.models.UserEntity;
import com.hostly.hostlyapp.models.dto.UserEntityDTO;
import com.hostly.hostlyapp.models.dto.response.AccommodationResponse;
import com.hostly.hostlyapp.models.dto.response.UserResponse;
import com.hostly.hostlyapp.models.dto.AccommodationDTO;
import com.hostly.hostlyapp.models.mappers.AccommodationMapper;
import com.hostly.hostlyapp.models.mappers.UserEntityMapper;
import com.hostly.hostlyapp.repositories.AccommodationRepository;
import com.hostly.hostlyapp.services.AccommodationService;
import com.hostly.hostlyapp.services.UserEntityService;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    @Autowired
    private AccommodationRepository repository;

    @Autowired
    private UserEntityService userService;

    @Autowired
    private AccommodationMapper mapper;

    @Autowired
    private UserEntityMapper userMapper;

    @Override
    public AccommodationResponse create(AccommodationDTO request) {
        UserResponse userDto = userService.getById(request.getUserId());
        Accommodation entity = mapper.accommodationDTOtoAccommodation(request);
        UserEntity userEntity = userMapper.userResponsetoUserEntity(userDto);
        entity.setUser(userEntity);
        repository.save(entity);
        return mapper.accommodationToAccommodationResponse(entity);
    }

    @Override
    public AccommodationResponse getById(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        Accommodation entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Accommodation not found"));
        return mapper.accommodationToAccommodationResponse(entity);
    }

    @Override
    public Collection<AccommodationResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::accommodationToAccommodationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AccommodationResponse update(UUID id, AccommodationDTO request) {
        if (id == null) {
            throw new BadRequestException("Invalid id");
        }
        Accommodation existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Accommodation not found"));

        // Actualization of fields
        existingEntity.setPrice(request.getPrice());
        existingEntity.setType(request.getType());
        existingEntity.setRooms(request.getRooms());
        existingEntity.setDescription(request.getDescription());
        existingEntity.setFotos(request.getFotos());
        existingEntity.setStatus(request.getStatus());

        // save the entity in database
        Accommodation updatedAccommodation = repository.save(existingEntity);

        // aqui podemos returnar tanto un DTO o simplemente un entidad.
        // ya que no tenemos datos sincibles
        return mapper.accommodationToAccommodationResponse(updatedAccommodation);
    }

    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        Accommodation accommodation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Accommodation not found"));
        repository.delete(accommodation);
    }
}
