package com.dreyer.bradescocodechallenge.web.controller;

import com.dreyer.bradescocodechallenge.web.dto.OrderDto;
import com.dreyer.bradescocodechallenge.web.presenter.PostCheckoutPresenter;
import com.dreyer.bradescocodechallenge.web.service.PostCheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class PostCheckoutController {

    private final PostCheckoutService checkoutService;
    private final PostCheckoutPresenter checkoutPresenter;

    @Autowired
    public PostCheckoutController(PostCheckoutService checkoutService, PostCheckoutPresenter checkoutPresenter) {
        this.checkoutService = checkoutService;
        this.checkoutPresenter = checkoutPresenter;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> checkout(@RequestBody OrderDto order) {
        this.checkoutService.execute(order);

        return checkoutPresenter.presenter();
    }
}
