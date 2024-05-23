package com.dreyer.bradescocodechallenge.business.gateway;

import com.dreyer.bradescocodechallenge.business.entity.Order;

public interface OrderGateway {
    Order create(Order order);
}
