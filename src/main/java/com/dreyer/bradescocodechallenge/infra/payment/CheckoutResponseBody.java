package com.dreyer.bradescocodechallenge.infra.payment;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseBody {
    private String value;
    private String url;
}
