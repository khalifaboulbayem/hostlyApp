package com.hostly.hostlyapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hostly.hostlyapp.models.dto.AccommodationDTO;
import com.hostly.hostlyapp.models.dto.response.AccommodationResponse;
import com.hostly.hostlyapp.models.mappers.AccommodationMapper;
import com.hostly.hostlyapp.services.AccommodationService;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccommodationController {

    @Autowired
    private AccommodationService service;

    @Autowired
    private AccommodationMapper mapper;

    @GetMapping("/rooms")
    public ResponseEntity<Collection<AccommodationResponse>> getAll() {
        Collection<AccommodationResponse> roomsList = service.getAll().stream()
                .map(mapper::accommodationDTOtoAccommodationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomsList);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<AccommodationResponse> details(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.accommodationDTOtoAccommodationResponse(service.getById(id)));

    }

    @PostMapping("/room")
    public ResponseEntity<AccommodationResponse> create(@RequestBody AccommodationDTO entity) {
        AccommodationResponse entityCreated = mapper.accommodationDTOtoAccommodationResponse(service.create(entity));
        return ResponseEntity.ok(entityCreated);
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<AccommodationResponse> update(@PathVariable UUID id, @RequestBody AccommodationDTO entity) {
        AccommodationResponse entityUpdated = mapper
                .accommodationDTOtoAccommodationResponse(service.update(id, entity));
        return ResponseEntity.ok(entityUpdated);
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
