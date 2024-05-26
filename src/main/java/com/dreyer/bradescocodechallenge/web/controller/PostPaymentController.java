package com.dreyer.bradescocodechallenge.web.controller;

import com.dreyer.bradescocodechallenge.web.dto.PaymentDto;
import com.dreyer.bradescocodechallenge.web.presenter.PostPaymentPresenter;
import com.dreyer.bradescocodechallenge.web.service.PostPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PostPaymentController {
    private final PostPaymentService postPaymentService;
    private final PostPaymentPresenter postPaymentPresenter;

    @Autowired
    public PostPaymentController(PostPaymentService postPaymentService, PostPaymentPresenter postPaymentPresenter) {
        this.postPaymentService = postPaymentService;
        this.postPaymentPresenter = postPaymentPresenter;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> realizePayment(@RequestBody PaymentDto paymentDto) {
        this.postPaymentService.execute(paymentDto);

        return postPaymentPresenter.presenter();
    }
}
