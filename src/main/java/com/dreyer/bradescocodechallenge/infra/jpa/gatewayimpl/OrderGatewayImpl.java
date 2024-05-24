package com.dreyer.bradescocodechallenge.infra.jpa.gatewayimpl;

import com.dreyer.bradescocodechallenge.business.domain.entity.Checkout;
import com.dreyer.bradescocodechallenge.business.domain.gateway.OrderGateway;
import org.springframework.stereotype.Service;

@Service
public class OrderGatewayImpl implements OrderGateway {
    @Override
    public Checkout create(Checkout checkout) {
        return Checkout.builder()
                .id(Long.MIN_VALUE)
                .requestId(checkout.getRequestId())
                .keyType(checkout.getKeyType())
                .key(checkout.getKey())
                .payee(checkout.getPayee())
                .city(checkout.getCity())
                .product(checkout.getProduct())
                .price(checkout.getPrice())
                .build();
    }
}
