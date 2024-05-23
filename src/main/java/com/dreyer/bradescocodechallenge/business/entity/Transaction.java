package com.dreyer.bradescocodechallenge.business.entity;

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
    private Order order;
    private String payer;
    private String payee;
    private BigDecimal value;
    private LocalDateTime createdAt;
}
