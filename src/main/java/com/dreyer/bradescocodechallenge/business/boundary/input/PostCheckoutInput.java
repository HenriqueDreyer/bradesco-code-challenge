package com.dreyer.bradescocodechallenge.business.boundary.input;

import com.dreyer.bradescocodechallenge.business.boundary.requestmodel.CheckoutRequestModel;

public interface PostCheckoutInput {
    void execute(CheckoutRequestModel requestModel);
}
