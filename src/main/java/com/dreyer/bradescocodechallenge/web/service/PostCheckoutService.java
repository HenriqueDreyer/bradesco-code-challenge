package com.dreyer.bradescocodechallenge.web.service;

import com.dreyer.bradescocodechallenge.business.boundary.input.PostCheckoutInput;
import com.dreyer.bradescocodechallenge.business.boundary.requestmodel.CheckoutRequestModel;
import com.dreyer.bradescocodechallenge.web.dto.CheckoutDto;
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

    public void execute(CheckoutDto checkout) {
        final var requestModel = CheckoutRequestModel.builder()
                .requestId(UUID.randomUUID())
                .keyType(checkout.getKeyType())
                .key(checkout.getKey())
                .payee(checkout.getPayee())
                .city(checkout.getCity())
                .product(checkout.getProduct())
                .price(checkout.getPrice().setScale(2))
                .build();

        checkoutInput.execute(requestModel);
    }
}
