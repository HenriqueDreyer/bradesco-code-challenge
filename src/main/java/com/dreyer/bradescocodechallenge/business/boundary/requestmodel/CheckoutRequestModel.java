package com.dreyer.bradescocodechallenge.business.boundary.requestmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequestModel {
    private UUID requestId;
    private int keyType;
    private String key;
    private String payer;
    private String city;
    private String product;
    private BigDecimal price;
}
