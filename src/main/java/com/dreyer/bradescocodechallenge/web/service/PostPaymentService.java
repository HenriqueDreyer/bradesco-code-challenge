package com.dreyer.bradescocodechallenge.web.service;

import com.dreyer.bradescocodechallenge.business.boundary.input.PostPaymentInput;
import com.dreyer.bradescocodechallenge.business.boundary.requestmodel.PaymentRequestModel;
import com.dreyer.bradescocodechallenge.web.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostPaymentService {
    private final PostPaymentInput postPaymentInput;

    @Autowired
    public PostPaymentService(PostPaymentInput postPaymentInput) {
        this.postPaymentInput = postPaymentInput;
    }

    public void execute(PaymentDto paymentDto) {
        this.postPaymentInput.execute(PaymentRequestModel.builder()
                .transactionId(paymentDto.getTransactionId())
                .build());
    }
}
