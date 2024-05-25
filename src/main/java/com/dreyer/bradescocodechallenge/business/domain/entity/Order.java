package com.dreyer.bradescocodechallenge.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private KeyType keyType;
    private String key;
    private String payer;
    private String city;
    private String product;
    private BigDecimal price;
}
