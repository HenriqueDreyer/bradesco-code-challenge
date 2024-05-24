package com.dreyer.bradescocodechallenge.infra.payment;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestModel {
    private int keyType;
    private String key;
    private String payee;
    private String city;
    private BigDecimal value;
    private String transactionId;
}
