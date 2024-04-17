package com.hostly.hostlyapp.services.impl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostly.hostlyapp.enums.AccommodationStatus;
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
    @Override
    public ReservationResponse create(ReservationDTO request) {

        // Verificar los datos
        LocalDate startDate = request.getStartDate();
        LocalDate finishDate = request.getEndDate();

        if (finishDate.isBefore(startDate)) {
            throw new FieldInvalidException("Check-in date must come before check-out date");
        }

        // Obetener la habitacion de la base de datos
        AccommodationDTO accoDtoExisting = accommodationMapper
                .accommodationResponseToAccommodationDTO(accommodationService.getById(request.getAccommodationId()));

        // -verficar sin esta disponible.
        if (!accoDtoExisting.getStatus().equals(AccommodationStatus.AVAILABLE)) {
            throw new BadRequestException("Accommodation is not available for reservation.");
        }

        // Si esta disponible si procede a guaradarla mapeando reservationDTO a
        // reservation entity.
        Reservation reservation = mapper.reservationDTOtoReservation(request);

        // obtener el usuario
        UserEntity userEntity = userMapper.userResponsetoUserEntity(userService.getById(request.getUserId()));

        // Cambiar el estado de la habitacion
        accoDtoExisting.setStatus(AccommodationStatus.BOOKED);

        // Guardar la modifcacion de la habitacion en base de datos
        AccommodationDTO updatedAccommodationDTO = accommodationMapper
                .accommodationResponseToAccommodationDTO(accommodationService.update(request.getAccommodationId(),
                        accoDtoExisting));

        // mapear la habitacion a una entidad.
        Accommodation accoEntity = accommodationMapper.accommodationDTOtoAccommodation(updatedAccommodationDTO);

        // establecer dicha habitacion a la resetva
        reservation.setAccommodation(accoEntity);
        reservation.setUser(userEntity);

        // Generar y establecer el código de confirmación
        String confirmationCode = generateConfirmationCode();

        reservation.setConfirmationCode(confirmationCode);

        // Crear el pago
        PaymentDTO paymentDto = getToPay(reservation.getStartDate(), reservation.getEndDate(), accoEntity.getPrice());
        Payment payment = paymentMapper.paymentDTOtoPayment(paymentDto);

        reservation.setPayment(payment);
        Reservation savedReservation = repository.save(reservation);
        return mapper.reservationToReservationResponse(savedReservation);
    }

    private PaymentDTO getToPay(LocalDate startDate, LocalDate endDate, double pricePerNight) {

        // Calcular el monto a pagar (puedes agregar lógica adicional aquí)
        double totalToPay = ((ChronoUnit.DAYS.between(startDate, endDate)) * pricePerNight);

        // Construir el DTO de pago
        PaymentDTO payment = PaymentDTO.builder()
                .amount(totalToPay)
                .build();
        return payment;
    }

    @Override
    public ReservationResponse getById(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        Reservation entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        return mapper.reservationToReservationResponse(entity);
    }

    @Override
    public Collection<ReservationResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::reservationToReservationResponse)
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
        Reservation entity = repository.findByConfirmationCode(confirmacionCode)
                .orElseThrow(() -> new NotFoundException(
                        "No reservation found with confirmation code :" + confirmacionCode));

        return entity;
    }

    protected double calculateTotalPrice(double pricePerNight, LocalDate startDate, LocalDate endDate) {
        long numberOfNights = ChronoUnit.DAYS.between(startDate, endDate);
        return pricePerNight * numberOfNights;
    }

}
