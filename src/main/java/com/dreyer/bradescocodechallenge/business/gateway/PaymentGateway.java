package com.dreyer.bradescocodechallenge.business.gateway;

import com.dreyer.bradescocodechallenge.business.entity.Order;
import com.dreyer.bradescocodechallenge.business.entity.Payment;

public interface PaymentGateway {
    Payment generatePayment(Order order);
}
