package com.hostly.hostlyapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hostly.hostlyapp.models.dto.ReservationDTO;
import com.hostly.hostlyapp.models.dto.response.ReservationResponse;
import com.hostly.hostlyapp.models.mappers.ReservationMapper;
import com.hostly.hostlyapp.services.ReservationService;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private ReservationService service;

    @Autowired
    private ReservationMapper mapper;

    @GetMapping("/reservations")
    public ResponseEntity<Collection<ReservationResponse>> getAll() {
        Collection<ReservationResponse> reservations = service.getAll().stream()
                .map(mapper::reservationDTOtoReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponse> details(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.reservationDTOtoReservationResponse(service.getById(id)));

    }

    @GetMapping("/reservation/{confirmationCode}")
    public ResponseEntity<ReservationResponse> details(@PathVariable("confirmationCode") String id) {
        return ResponseEntity.ok(mapper.reservationToReservationResponse(service.getReservationByCodeConfirmation(id)));

    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationDTO entity) {
        ReservationResponse entityCreated = mapper.reservationDTOtoReservationResponse(service.create(entity));
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
