package com.dreyer.bradescocodechallenge.business.boundary.input;

import com.dreyer.bradescocodechallenge.infra.payment.PaymentRequestModel;

public interface PostPaymentInput {
    void execute(PaymentRequestModel requestModel);
}
