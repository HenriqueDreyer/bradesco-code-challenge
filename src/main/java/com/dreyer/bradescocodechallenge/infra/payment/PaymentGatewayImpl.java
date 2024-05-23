package com.dreyer.bradescocodechallenge.infra.payment;

import com.dreyer.bradescocodechallenge.business.entity.Order;
import com.dreyer.bradescocodechallenge.business.entity.Payment;
import com.dreyer.bradescocodechallenge.business.gateway.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayImpl implements PaymentGateway {
    @Override
    public Payment generatePayment(Order order) {
        return null;
    }
}
