package com.dreyer.bradescocodechallenge.web.presenter;

import com.dreyer.bradescocodechallenge.business.boundary.output.PostPaymentOutput;
import com.dreyer.bradescocodechallenge.business.boundary.responsemodel.PaymentResponseModel;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostPaymentPresenter implements PostPaymentOutput {
    private final ThreadLocal<List<ErrorResponseModel>> errorsThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<PaymentResponseModel> sucessThreadLocal = new ThreadLocal<>();

    @Override
    public void success(PaymentResponseModel responseModel) {
        this.sucessThreadLocal.set(responseModel);
    }

    @Override
    public void error(List<ErrorResponseModel> errors) {
        this.errorsThreadLocal.set(errors);
    }

    public ResponseEntity<Object> presenter() {
        try {

            if(errorsThreadLocal.get() != null && !errorsThreadLocal.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsThreadLocal.get());
            }

            return ResponseEntity.status(HttpStatus.OK).body(sucessThreadLocal.get());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            errorsThreadLocal.remove();
            sucessThreadLocal.remove();
        }
    }
}
