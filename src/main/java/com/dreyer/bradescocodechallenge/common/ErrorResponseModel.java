package com.dreyer.bradescocodechallenge.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponseModel {
    private String message;
}
