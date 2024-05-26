package com.dreyer.bradescocodechallenge.business.boundary.requestmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestModel {
    private Long transactionId;
}
