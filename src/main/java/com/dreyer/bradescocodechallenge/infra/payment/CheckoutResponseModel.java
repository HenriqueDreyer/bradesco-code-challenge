package com.dreyer.bradescocodechallenge.infra.payment;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseModel {
    private String value;
    private String url;
}
