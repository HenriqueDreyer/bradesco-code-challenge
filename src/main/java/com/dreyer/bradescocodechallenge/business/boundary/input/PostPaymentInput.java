package com.dreyer.bradescocodechallenge.business.boundary.input;

import com.dreyer.bradescocodechallenge.business.boundary.requestmodel.PaymentRequestModel;

public interface PostPaymentInput {
    void execute(PaymentRequestModel requestModel);
}
