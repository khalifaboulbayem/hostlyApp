package com.hostly.hostlyapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hostly.hostlyapp.models.dto.AccommodationDTO;
import com.hostly.hostlyapp.models.dto.response.AccommodationResponse;
import com.hostly.hostlyapp.services.AccommodationService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccommodationController {

    @Autowired
    private AccommodationService service;

    @GetMapping("/rooms")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<?> details(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));

    }

    @PostMapping("/room")
    public ResponseEntity<?> create(@RequestBody AccommodationDTO entity) {
        AccommodationResponse entityCreated = service.create(entity);
        return ResponseEntity.ok(entityCreated);
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody AccommodationDTO entity) {
        AccommodationResponse entityUpdated = service.update(id, entity);
        return ResponseEntity.ok(entityUpdated);
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
