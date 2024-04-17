package com.hostly.hostlyapp.controllers;

import com.hostly.hostlyapp.models.dto.PaymentDTO;
import com.hostly.hostlyapp.models.dto.response.PaymentResponse;
import com.hostly.hostlyapp.models.mappers.PaymentMapper;
import com.hostly.hostlyapp.services.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper mapper;

    @GetMapping("/payments")
    public ResponseEntity<Collection<PaymentDTO>> getAllPayments() {
        Collection<PaymentDTO> payments = paymentService.getAll();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping("/payments/reservation/{reservationId}")
    public ResponseEntity<PaymentResponse> makePaymentByReservationId(@PathVariable UUID reservationId,
            @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO payment = paymentService.makePayment(reservationId, paymentDTO);
        PaymentResponse response = mapper.paymentDtoToPaymentResponse(payment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // @GetMapping("/payments/reservation/{reservationId}")
    // public ResponseEntity<PaymentResponse>
    // getPaymentToPayByReservationId(@PathVariable UUID reservationId) {
    // PaymentDTO payment = paymentService.getToPay(reservationId);
    // PaymentResponse response = mapper.paymentDtoToPaymentResponse(payment);
    // return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    @PostMapping("/payments/confirmation/{confirmationCode}")
    public ResponseEntity<PaymentResponse> makePaymentByConfirmationCode(@PathVariable String confirmationCode,
            @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO payment = paymentService.makePayment(confirmationCode, paymentDTO);
        PaymentResponse response = mapper.paymentDtoToPaymentResponse(payment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
