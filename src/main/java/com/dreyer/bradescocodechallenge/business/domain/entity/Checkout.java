package com.dreyer.bradescocodechallenge.business.domain.entity;

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
public class Checkout {
    private Long id;
    private UUID requestId;
    private KeyType keyType;
    private String key;
    private String payee;
    private String city;
    private String product;
    private BigDecimal price;
}
