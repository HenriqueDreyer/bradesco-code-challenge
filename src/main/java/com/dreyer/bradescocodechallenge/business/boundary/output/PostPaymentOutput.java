package com.dreyer.bradescocodechallenge.business.boundary.output;

import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;
import com.dreyer.bradescocodechallenge.infra.payment.PaymentResponseModel;

import java.util.List;

public interface PostPaymentOutput {
    void success(PaymentResponseModel responseModel);
    void error(List<ErrorResponseModel> errors);
}
