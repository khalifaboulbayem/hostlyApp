package com.hostly.hostlyapp.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hostly.hostlyapp.models.Payment;
import com.hostly.hostlyapp.models.dto.PaymentDTO;
import com.hostly.hostlyapp.models.dto.response.PaymentResponse;

@Mapper(componentModel = "spring", uses = ReservationMapper.class)
public interface PaymentMapper {

    Payment paymentDTOtoPayment(PaymentDTO paymentDTO);

    PaymentDTO paymentToPaymentDTO(Payment payment);

    PaymentResponse paymentToPaymentResponse(Payment payment);

    PaymentResponse paymentDtoToPaymentResponse(PaymentDTO payment);
}
