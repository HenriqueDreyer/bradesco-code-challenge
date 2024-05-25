package com.dreyer.bradescocodechallenge.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long id;
    private Long orderId;
    private TransactionStatus status;
    private String payer;
    private String payee;
    private BigDecimal value;
    private LocalDateTime createdAt;
}
