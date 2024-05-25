package com.dreyer.bradescocodechallenge.infra.payment;

public class CheckoutException extends RuntimeException {
    public CheckoutException(String message) {
        super(message);
    }
}
