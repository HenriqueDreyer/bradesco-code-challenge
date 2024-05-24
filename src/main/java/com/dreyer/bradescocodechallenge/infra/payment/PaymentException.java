package com.dreyer.bradescocodechallenge.infra.payment;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
