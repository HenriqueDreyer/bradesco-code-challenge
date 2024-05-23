package com.dreyer.bradescocodechallenge.business.input;

import com.dreyer.bradescocodechallenge.business.requestmodel.OrderRequestModel;

public interface PostCheckoutInput {
    void execute(OrderRequestModel requestModel);
}
