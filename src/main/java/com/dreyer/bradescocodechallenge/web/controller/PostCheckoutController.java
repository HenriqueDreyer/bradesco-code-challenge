package com.dreyer.bradescocodechallenge.web.controller;

import com.dreyer.bradescocodechallenge.web.dto.CheckoutDto;
import com.dreyer.bradescocodechallenge.web.presenter.PostCheckoutPresenter;
import com.dreyer.bradescocodechallenge.web.service.PostCheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/checkout")
public class PostCheckoutController {

    private final PostCheckoutService checkoutService;
    private final PostCheckoutPresenter checkoutPresenter;

    @Autowired
    public PostCheckoutController(PostCheckoutService checkoutService, PostCheckoutPresenter checkoutPresenter) {
        this.checkoutService = checkoutService;
        this.checkoutPresenter = checkoutPresenter;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Object> checkout(@RequestBody CheckoutDto order) {
        this.checkoutService.execute(order);

        return checkoutPresenter.presenter();
    }
}
