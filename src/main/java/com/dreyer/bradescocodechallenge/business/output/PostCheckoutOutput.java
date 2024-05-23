package com.dreyer.bradescocodechallenge.business.output;

import com.dreyer.bradescocodechallenge.business.responsemodel.PaymentMethodResponseModel;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;

import java.util.List;

public interface PostCheckoutOutput {
    void success(PaymentMethodResponseModel responseModel);
    void error(List<ErrorResponseModel> errors);
}
