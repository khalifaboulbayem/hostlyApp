package com.hostly.hostlyapp.services.impl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import com.hostly.hostlyapp.enums.AccommodationStatus;
import com.hostly.hostlyapp.handlers.exceptions.BadRequestException;
import com.hostly.hostlyapp.handlers.exceptions.FieldInvalidException;
import com.hostly.hostlyapp.handlers.exceptions.NotFoundException;
import com.hostly.hostlyapp.models.Reservation;
import com.hostly.hostlyapp.models.dto.AccommodationDTO;
import com.hostly.hostlyapp.models.dto.ReservationDTO;
import com.hostly.hostlyapp.models.mappers.AccommodationMapper;
import com.hostly.hostlyapp.models.mappers.ReservationMapper;
import com.hostly.hostlyapp.models.mappers.UserMapper;
import com.hostly.hostlyapp.repositories.ReservationRepository;
import com.hostly.hostlyapp.services.AccommodationService;
import com.hostly.hostlyapp.services.ReservationService;
import com.hostly.hostlyapp.services.UserService;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private ReservationMapper mapper;

    @Autowired
    private AccommodationMapper accommodationMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ReservationDTO create(ReservationDTO request) {

        // Verificar los datos
        LocalDate startDate = request.getStartDate();
        LocalDate finishDate = request.getEndDate();

        if (finishDate.isBefore(startDate)) {
            throw new FieldInvalidException("Check-in date must come before check-out date");
        }
        AccommodationDTO accoDto = accommodationService.getById(request.getAccommodationId());
        if (!accoDto.getStatus().equals(AccommodationStatus.AVAILABLE)) {
            throw new RequestRejectedException("Accommodation is not available for reservation.");
        }

        Reservation reservation = mapper.reservationDTOtoReservation(request);
        reservation.setAccommodation(accommodationMapper.accommodationDTOtoAccommodation(accoDto));
        reservation.setUser(userMapper.userDTOtoUser(userService.getById(request.getUserId())));
        // Guardar la reserva

        // Actualizar el estado del alojamiento
        accoDto.setStatus(AccommodationStatus.BOOKED);
        AccommodationDTO updatedAccommodationDTO = accommodationService.update(request.getAccommodationId(), accoDto);

        // Generar y establecer el código de confirmación
        String confirmationCode = generateConfirmationCode();
        reservation.setConfirmationCode(confirmationCode);

        Reservation savedReservation = repository.save(reservation);
        return mapper.reservationToReservationDTO(savedReservation);
    }

    @Override
    public ReservationDTO getById(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        Reservation entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        return mapper.reservationToReservationDTO(entity);
    }

    @Override
    public Collection<ReservationDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::reservationToReservationDTO)
                .collect(Collectors.toList());
    }

    // @Override
    // public ReservationDTO update(UUID id, ReservationDTO request) {
    // if (id == null) {
    // throw new BadRequestException("Invalid id");
    // }
    // Reservation existingEntity = repository.findById(id)
    // .orElseThrow(() -> new NotFoundException("Reservation not found"));

    // // Actualization of fields

    // // save the entity in database
    // Reservation updatedReservation = repository.save(existingEntity);

    // return mapper.reservationToReservationDTO(updatedReservation);
    // }

    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        repository.delete(reservation);
    }

    private String generateConfirmationCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Reservation getReservationByCodeConfirmation(String confirmacionCode) {
        if (confirmacionCode.isEmpty()) {
            throw new BadRequestException("Invalid Confirmacion code");
        }
        return repository.findByConfirmationCode(confirmacionCode)
                .orElseThrow(() -> new NotFoundException(
                        "No reservation found with confirmation code :" + confirmacionCode));
    }
}
