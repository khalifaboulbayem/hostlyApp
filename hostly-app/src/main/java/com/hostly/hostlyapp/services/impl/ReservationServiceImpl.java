package com.hostly.hostlyapp.services.impl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hostly.hostlyapp.enums.AccommodationStatus;
import com.hostly.hostlyapp.enums.PaymentStatus;
import com.hostly.hostlyapp.enums.ReservationStatus;
import com.hostly.hostlyapp.handlers.exceptions.BadRequestException;
import com.hostly.hostlyapp.handlers.exceptions.FieldAlreadyExistException;
import com.hostly.hostlyapp.handlers.exceptions.FieldInvalidException;
import com.hostly.hostlyapp.handlers.exceptions.NotFoundException;
import com.hostly.hostlyapp.models.Accommodation;
import com.hostly.hostlyapp.models.Payment;
import com.hostly.hostlyapp.models.Reservation;
import com.hostly.hostlyapp.models.UserEntity;
import com.hostly.hostlyapp.models.dto.ReservationDTO;
import com.hostly.hostlyapp.models.dto.response.ReservationResponse;
import com.hostly.hostlyapp.models.dto.AccommodationDTO;
import com.hostly.hostlyapp.models.dto.PaymentDTO;
import com.hostly.hostlyapp.models.mappers.AccommodationMapper;
import com.hostly.hostlyapp.models.mappers.PaymentMapper;
import com.hostly.hostlyapp.models.mappers.ReservationMapper;
import com.hostly.hostlyapp.models.mappers.UserEntityMapper;
import com.hostly.hostlyapp.repositories.PaymentRepository;
import com.hostly.hostlyapp.repositories.ReservationRepository;
import com.hostly.hostlyapp.services.AccommodationService;
import com.hostly.hostlyapp.services.PaymentService;
import com.hostly.hostlyapp.services.ReservationService;
import com.hostly.hostlyapp.services.UserEntityService;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserEntityService userService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private ReservationMapper mapper;
    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private AccommodationMapper accommodationMapper;

    @Autowired
    private UserEntityMapper userMapper;

    /*
     * para crear una reserva hay que:
     * 1- verificar si existe la habitacion, y recuperarla
     * 2- recuperar el usuario
     * 3- ver
     */
    @Transactional
    @Override
    public ReservationDTO create(ReservationDTO request) {

        // Verificar los datos
        LocalDate startDate = request.getStartDate();
        LocalDate finishDate = request.getEndDate();

        if (finishDate.isBefore(startDate)) {
            throw new FieldInvalidException("Check-in date must come before check-out date");
        }

        // Obetener la habitacion de la base de datos
        AccommodationDTO accoDtoExisting = accommodationService.getById(request.getAccommodationId());

        // Verificar si está disponible.
        if (!accoDtoExisting.getStatus().equals(AccommodationStatus.AVAILABLE)) {
            throw new BadRequestException("Accommodation is not available for reservation.");
        }

        // Cambiar el estado de la habitación a reservado
        accoDtoExisting.setStatus(AccommodationStatus.BOOKED);
        accoDtoExisting = accommodationService.update(request.getAccommodationId(),
                accoDtoExisting);

        // Obtener el usuario
        UserEntity userEntity = userMapper.userResponsetoUserEntity(userService.getById(request.getUserId()));

        // Crear la reserva y establecer la habitación y el usuario
        Reservation reservation = mapper.reservationDTOtoReservation(request);
        reservation.setAccommodation(accommodationMapper.accommodationDTOtoAccommodation(accoDtoExisting));
        reservation.setUser(userEntity);
        reservation.setStatus(ReservationStatus.IN_PROGRESS);

        // Crear el pago
        // PaymentDTO paymentDto = getToPay(reservation.getStartDate(),
        // reservation.getEndDate(), accoDtoExisting.getPrice());

        // paymentDto.setStatus(PaymentStatus.IN_PROGRESS);
        // Payment payment = paymentMapper.paymentDTOtoPayment(paymentDto);
        // Guardar el pago y la reserva
        // Payment savedPayment = paymentRepository.save(payment);
        // reservation.setPayment(savedPayment);

        // Guardar la reserva
        Reservation savedReservation = repository.save(reservation);
        return mapper.reservationToReservationDTO(savedReservation);
    }

    // Método para calcular el monto a pagar
    private PaymentDTO getToPay(LocalDate startDate, LocalDate endDate, double pricePerNight) {
        double totalToPay = ChronoUnit.DAYS.between(startDate, endDate) * pricePerNight;
        return PaymentDTO.builder().amount(totalToPay).build();
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

    @Override
    public ReservationDTO updateStatus(UUID id, ReservationStatus status) {
        if (id == null) {
            throw new BadRequestException("Invalid id");
        }
        Reservation existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        existingEntity.setStatus(status);
        Reservation updatedReservation = repository.save(existingEntity);
        return mapper.reservationToReservationDTO(updatedReservation);
    }

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
        Reservation entity = repository.findByConfirmationCode(confirmacionCode)
                .orElseThrow(() -> new NotFoundException(
                        "No reservation found with confirmation code :" + confirmacionCode));

        return entity;
    }


}
