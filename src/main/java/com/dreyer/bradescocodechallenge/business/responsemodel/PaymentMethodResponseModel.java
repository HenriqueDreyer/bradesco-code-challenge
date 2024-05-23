package com.dreyer.bradescocodechallenge.business.responsemodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentMethodResponseModel {
    private String value;
}
