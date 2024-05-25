package com.dreyer.bradescocodechallenge.infra.payment;

public class PixPaymentSystemException extends RuntimeException {
    public PixPaymentSystemException(String message) {
        super(message);
    }
}
