package com.hostly.hostlyapp.services.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostly.hostlyapp.handlers.exceptions.*;
import com.hostly.hostlyapp.handlers.exceptions.FieldInvalidException;
import com.hostly.hostlyapp.models.Payment;
import com.hostly.hostlyapp.models.Reservation;
import com.hostly.hostlyapp.models.dto.PaymentDTO;
import com.hostly.hostlyapp.models.dto.response.PaymentResponse;
import com.hostly.hostlyapp.models.mappers.PaymentMapper;
import com.hostly.hostlyapp.models.mappers.ReservationMapper;
import com.hostly.hostlyapp.repositories.PaymentRepository;
import com.hostly.hostlyapp.services.PaymentService;
import com.hostly.hostlyapp.services.ReservationService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private PaymentMapper mapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private ReservationService reservationService;

    @Override
    public Collection<PaymentDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::paymentToPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO update(UUID id, PaymentDTO entityDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public PaymentDTO makePayment(String confirmacionCode, PaymentDTO entityDto) {

        // Calcular el precio total basado en el precio por noche y el número de noches
        Reservation reservation = reservationService.getReservationByCodeConfirmation(confirmacionCode);
        return savePayement(entityDto, reservation);
    }

    @Override
    public PaymentDTO makePayment(UUID reservationId, PaymentDTO entityDto) {
        // Calcular el precio total basado en el precio por noche y el número de noches
        Reservation reservation = reservationMapper
                .reservationDTOtoReservation(reservationService.getById(reservationId));
        return savePayement(entityDto, reservation);
    }

    private PaymentDTO savePayement(PaymentDTO entityDto, Reservation reservation) {

        double totalPrice = calculateTotalPrice(reservation.getAccommodation().getPrice(),
                reservation.getStartDate(),
                reservation.getEndDate());

        if (entityDto.getAmount() != totalPrice) {
            throw new FieldInvalidException("Invalid amount, the cost of the reservation it is: " + totalPrice);
        }

        Payment entity = mapper.paymentDTOtoPayment(entityDto);
        entity.setReservation(reservation);
        entity.setAmount(totalPrice);
        entity.setPaymentDate(LocalDate.now()); // Establecer la fecha actual como fecha de pago

        // Guardar el pago en la base de datos
        Payment savedPayment = repository.save(entity);

        return mapper.paymentToPaymentDTO(savedPayment);
    }

    @Override
    public PaymentDTO getToPay(UUID reservationId) {
        if (reservationId == null) {
            throw new BadRequestException("Invalid reservation id");
        }

        Reservation reservation = reservationMapper
                .reservationDTOtoReservation(reservationService.getById(reservationId));
        // Verificar si la reserva ya tiene un pago asociado
        if (reservation.getPayment() != null) {
            throw new FieldAlreadyExistException(
                    "Payment already exists for reservation with code: " +
                            reservation.getConfirmationCode());
        }

        // Calcular el monto a pagar (puedes agregar lógica adicional aquí)
        double pricePerNight = reservation.getAccommodation().getPrice();
        double totalToPay = calculateTotalPrice(pricePerNight,
                reservation.getStartDate(), reservation.getEndDate());

        // Construir el DTO de pago
        PaymentDTO payment = PaymentDTO.builder()
                .amount(totalToPay)
               // .
                .build();
        return payment;
    }

    private double calculateTotalPrice(double pricePerNight, LocalDate startDate, LocalDate endDate) {
        long numberOfNights = ChronoUnit.DAYS.between(startDate, endDate);
        return pricePerNight * numberOfNights;
    }

}
