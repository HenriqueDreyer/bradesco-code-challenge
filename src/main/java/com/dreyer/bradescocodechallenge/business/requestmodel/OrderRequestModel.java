package com.dreyer.bradescocodechallenge.business.requestmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderRequestModel {
    private UUID cartId;
    private String product;
    private BigDecimal price;
}
