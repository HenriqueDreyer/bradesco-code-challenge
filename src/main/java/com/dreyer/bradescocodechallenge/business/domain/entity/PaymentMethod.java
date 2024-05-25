package com.dreyer.bradescocodechallenge.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    private String value;
    private String transactionId;
}
