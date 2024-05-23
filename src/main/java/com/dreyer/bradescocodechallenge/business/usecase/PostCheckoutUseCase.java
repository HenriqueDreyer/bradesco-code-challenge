package com.dreyer.bradescocodechallenge.business.usecase;

import com.dreyer.bradescocodechallenge.business.input.PostCheckoutInput;
import com.dreyer.bradescocodechallenge.business.output.PostCheckoutOutput;
import com.dreyer.bradescocodechallenge.business.requestmodel.OrderRequestModel;
import com.dreyer.bradescocodechallenge.business.responsemodel.PaymentMethodResponseModel;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;

@Named
public class PostCheckoutUseCase implements PostCheckoutInput {
    private final PostCheckoutOutput checkoutOutput;

    @Inject
    public PostCheckoutUseCase(PostCheckoutOutput checkoutOutput) {
        this.checkoutOutput = checkoutOutput;
    }

    @Override
    public void execute(OrderRequestModel requestModel) {
        var errors = new ArrayList<ErrorResponseModel>();

        if (requestModel.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.add(ErrorResponseModel.builder()
                    .message("Preço do produto invalido")
                    .build());
        }

        if(requestModel.getProduct() == null || requestModel.getProduct().isBlank()) {
            errors.add(ErrorResponseModel.builder()
                    .message("Nome do produto invalido")
                    .build());
        }

        if(!errors.isEmpty()) {
            this.checkoutOutput.error(errors);
            return;
        }

        var responseModel = PaymentMethodResponseModel.builder()
                .value("Com sucesso")
                .build();

        this.checkoutOutput.success(responseModel);
    }
}
