package com.dreyer.bradescocodechallenge.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Long transactionId;
    private Long orderId;
    private TransactionStatus status;
}
