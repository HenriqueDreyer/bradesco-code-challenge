package com.dreyer.bradescocodechallenge.infra.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequestBody {
    private int keyType;
    private String key;
    private String payer;
    private String city;
    private BigDecimal value;
}
