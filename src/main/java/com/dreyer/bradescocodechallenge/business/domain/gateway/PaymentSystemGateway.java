package com.dreyer.bradescocodechallenge.business.domain.gateway;

import com.dreyer.bradescocodechallenge.business.domain.entity.Checkout;
import com.dreyer.bradescocodechallenge.business.domain.entity.Payment;
import com.dreyer.bradescocodechallenge.business.domain.entity.PaymentMethod;
import com.dreyer.bradescocodechallenge.business.domain.entity.Transaction;

import java.util.concurrent.CompletableFuture;

public interface PaymentSystemGateway {
    PaymentMethod generatePayment(Checkout checkout);
    CompletableFuture<Payment> realizePayment(Transaction transaction);
}
