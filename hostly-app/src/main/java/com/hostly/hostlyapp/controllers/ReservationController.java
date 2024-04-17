package com.hostly.hostlyapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hostly.hostlyapp.models.dto.ReservationDTO;
import com.hostly.hostlyapp.models.dto.response.ReservationResponse;
import com.hostly.hostlyapp.services.ReservationService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private ReservationService service;

    @GetMapping("/reservations")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<?> details(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));

    }

    @GetMapping("/reservation/{confirmationCode}")
    public ResponseEntity<?> details(@PathVariable("confirmationCode") String id) {
        return ResponseEntity.ok(service.getReservationByCodeConfirmation(id));
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> create(@RequestBody ReservationDTO entity) {
        ReservationResponse entityCreated = service.create(entity);
        return ResponseEntity.ok(entityCreated);
    }

    // @PutMapping("/reservation/{id}")
    // public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody
    // ReservationDTO entity) {
    // ReservationDTO entityUpdated = service.update(id, entity);
    // return ResponseEntity.ok(entityUpdated);
    // }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
