package com.dreyer.bradescocodechallenge.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {
    private int keyType;
    private String key;
    private String payee;
    private String city;
    private String product;
    private BigDecimal price;
}
