package com.dreyer.bradescocodechallenge.business.domain.gateway;

import com.dreyer.bradescocodechallenge.business.domain.entity.Order;

public interface OrderGateway {
    Order create(Order order);
}
