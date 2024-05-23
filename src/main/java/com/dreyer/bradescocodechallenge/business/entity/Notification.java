package com.dreyer.bradescocodechallenge.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long id;
    private String message;
    private Long orderId;
}
