package com.dreyer.bradescocodechallenge.business.boundary.responsemodel;

import com.dreyer.bradescocodechallenge.business.domain.entity.TransactionStatus;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseModel {
    private Long transactionId;
    private Long orderId;
    private String status;
}
