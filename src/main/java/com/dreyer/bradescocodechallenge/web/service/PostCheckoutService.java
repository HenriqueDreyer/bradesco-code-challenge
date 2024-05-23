package com.dreyer.bradescocodechallenge.web.service;

import com.dreyer.bradescocodechallenge.business.input.PostCheckoutInput;
import com.dreyer.bradescocodechallenge.business.requestmodel.OrderRequestModel;
import com.dreyer.bradescocodechallenge.web.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class PostCheckoutService {

    private final PostCheckoutInput checkoutInput;

    @Autowired
    public PostCheckoutService(PostCheckoutInput checkoutInput) {
        this.checkoutInput = checkoutInput;
    }

    public void execute(OrderDto order) {
        final var requestModel = OrderRequestModel.builder()
                .cartId(UUID.randomUUID())
                .product(order.getProduct())
                .price(order.getPrice().setScale(2))
                .build();

        checkoutInput.execute(requestModel);
    }
}
