package com.dreyer.bradescocodechallenge.web.presenter;

import com.dreyer.bradescocodechallenge.business.output.PostCheckoutOutput;
import com.dreyer.bradescocodechallenge.business.responsemodel.PaymentMethodResponseModel;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostCheckoutPresenter implements PostCheckoutOutput {
    private final ThreadLocal<List<ErrorResponseModel>> errorsThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<PaymentMethodResponseModel> sucessThreadLocal = new ThreadLocal<>();

    public void error(List<ErrorResponseModel> errors) {
        errorsThreadLocal.set(errors);
    }

    public void success(PaymentMethodResponseModel responseModel) {
        sucessThreadLocal.set(responseModel);
    }

    public ResponseEntity<Object> presenter() {
        try {

            if(errorsThreadLocal.get() != null && !errorsThreadLocal.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsThreadLocal.get());
            }

            return ResponseEntity.status(HttpStatus.OK).body(sucessThreadLocal.get());

        } finally {
            errorsThreadLocal.remove();
            sucessThreadLocal.remove();
        }
    }
}
