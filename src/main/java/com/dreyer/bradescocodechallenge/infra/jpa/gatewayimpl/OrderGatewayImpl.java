package com.dreyer.bradescocodechallenge.infra.jpa.gatewayimpl;

import com.dreyer.bradescocodechallenge.business.domain.entity.Order;
import com.dreyer.bradescocodechallenge.business.domain.gateway.OrderGateway;
import com.dreyer.bradescocodechallenge.infra.jpa.entity.OrderEntity;
import com.dreyer.bradescocodechallenge.infra.jpa.repository.OrderEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderGatewayImpl implements OrderGateway {
    private final OrderEntityRepository orderEntityRepository;

    @Autowired
    public OrderGatewayImpl(OrderEntityRepository orderEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
    }


    @Override
    public Order create(Order order) {
        var entity = OrderEntity.builder()
                .keyType(order.getKeyType().getValue())
                .userKey(order.getKey())
                .payer(order.getPayer())
                .city(order.getCity())
                .product(order.getProduct())
                .price(order.getPrice())
                .build();

        final var nEntity = this.orderEntityRepository.save(entity);

        return Order.builder()
                .id(nEntity.getId())
                .keyType(order.getKeyType())
                .key(order.getKey())
                .payer(order.getPayer())
                .city(order.getCity())
                .product(order.getProduct())
                .price(order.getPrice())
                .build();
    }
}
