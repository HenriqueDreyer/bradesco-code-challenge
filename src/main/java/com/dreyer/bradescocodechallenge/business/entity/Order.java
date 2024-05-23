package com.dreyer.bradescocodechallenge.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private UUID cardId;
    private String product;
    private BigDecimal price;
}
