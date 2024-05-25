package com.dreyer.bradescocodechallenge.infra.payment;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestModel {
    private Long transactionId;
    private BigDecimal value;
}
