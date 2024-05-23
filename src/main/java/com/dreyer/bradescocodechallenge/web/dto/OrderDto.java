package com.dreyer.bradescocodechallenge.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class OrderDto {
    private String product;
    private BigDecimal price;
}
