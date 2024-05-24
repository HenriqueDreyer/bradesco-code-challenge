package com.dreyer.bradescocodechallenge.business.domain.gateway;

import com.dreyer.bradescocodechallenge.business.domain.entity.Checkout;

public interface OrderGateway {
    Checkout create(Checkout checkout);
}
