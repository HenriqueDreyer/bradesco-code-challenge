package com.dreyer.bradescocodechallenge.business.boundary.output;

import com.dreyer.bradescocodechallenge.business.boundary.responsemodel.PaymentMethodResponseModel;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;

import java.util.List;

public interface PostCheckoutOutput {
    void success(PaymentMethodResponseModel responseModel);
    void error(List<ErrorResponseModel> errors);
}
