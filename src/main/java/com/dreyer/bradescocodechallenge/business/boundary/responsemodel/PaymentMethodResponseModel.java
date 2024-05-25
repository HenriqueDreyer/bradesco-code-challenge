package com.dreyer.bradescocodechallenge.business.boundary.responsemodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentMethodResponseModel {
    private String value;
    private String transactionId;
}
