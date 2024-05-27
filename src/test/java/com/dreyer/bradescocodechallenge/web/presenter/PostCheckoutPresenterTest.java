package com.dreyer.bradescocodechallenge.web.presenter;

import com.dreyer.bradescocodechallenge.business.boundary.responsemodel.PaymentMethodResponseModel;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostCheckoutPresenterTest {
    private PostCheckoutPresenter checkoutPresenter;

    private String value = "Unit-Test";
    private String transactionId = "Unit-Test";

    @BeforeEach
    void setUp() {
        this.checkoutPresenter = new PostCheckoutPresenter();
    }

    @Test
    public void shouldPresentCorrectlyWhenSuccess() {
        final var responseModel = PaymentMethodResponseModel.builder()
                .value(value)
                .transactionId(transactionId)
                .build();

        this.checkoutPresenter.success(responseModel);

        final var response = this.checkoutPresenter.presenter();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(byte[].class);
    }

    @Test
    public void shouldPresenterOnErrorWithStatus400() {
        final var errors = Collections.singletonList(ErrorResponseModel.builder()
                .message("Erro interno")
                .build());

        this.checkoutPresenter.error(errors);

        final var response = this.checkoutPresenter.presenter();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }
}