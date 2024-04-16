package com.hostly.hostlyapp.services;

import java.util.*;

import com.hostly.hostlyapp.models.dto.PaymentDTO;

public interface PaymentService {

    Collection<PaymentDTO> getAll();

    // PaymentDTO getById(UUID id);

    // Implementar el metodo solo para cambiar el estado del pago.
    // El princio los pagos pueden ser completados, cancelado, rembosado, fallido o
    // en proceso

    PaymentDTO update(UUID id, PaymentDTO entityDto);

    PaymentDTO makePayment(String confirmationCode, PaymentDTO entityDto);

    PaymentDTO makePayment(UUID reservationId, PaymentDTO entityDto);

    PaymentDTO getToPay(UUID reservationId);

    // lo pagos es prefereble que si tenga un historial de ellos.
    // void delete(UUID id);

}
