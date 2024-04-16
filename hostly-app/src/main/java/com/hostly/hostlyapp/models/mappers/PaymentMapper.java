package com.hostly.hostlyapp.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hostly.hostlyapp.models.Payment;
import com.hostly.hostlyapp.models.dto.PaymentDTO;

@Mapper(componentModel = "spring", uses = ReservationMapper.class)
public interface PaymentMapper {
    @Mapping(target = "id", ignore = true)
    Payment paymentDTOtoPayment(PaymentDTO paymentDTO);

    PaymentDTO paymentToPaymentDTO(Payment payment);
}
