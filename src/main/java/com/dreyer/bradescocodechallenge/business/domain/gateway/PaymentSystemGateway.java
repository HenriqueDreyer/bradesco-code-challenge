package com.dreyer.bradescocodechallenge.business.domain.gateway;

import com.dreyer.bradescocodechallenge.business.domain.entity.Checkout;
import com.dreyer.bradescocodechallenge.business.domain.entity.Payment;
import com.dreyer.bradescocodechallenge.business.domain.entity.PaymentMethod;
import com.dreyer.bradescocodechallenge.business.domain.entity.Transaction;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface PaymentSystemGateway {
    PaymentMethod generatePayment(Checkout checkout);
    Payment realizePayment(Transaction transaction);
}
