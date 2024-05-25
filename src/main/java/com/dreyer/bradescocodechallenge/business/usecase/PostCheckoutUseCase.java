package com.dreyer.bradescocodechallenge.business.usecase;

import com.dreyer.bradescocodechallenge.business.boundary.input.PostCheckoutInput;
import com.dreyer.bradescocodechallenge.business.boundary.output.PostCheckoutOutput;
import com.dreyer.bradescocodechallenge.business.boundary.requestmodel.CheckoutRequestModel;
import com.dreyer.bradescocodechallenge.business.boundary.responsemodel.PaymentMethodResponseModel;
import com.dreyer.bradescocodechallenge.business.domain.entity.*;
import com.dreyer.bradescocodechallenge.business.domain.gateway.OrderGateway;
import com.dreyer.bradescocodechallenge.business.domain.gateway.PaymentGateway;
import com.dreyer.bradescocodechallenge.business.domain.gateway.TransactionGateway;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

@Named
public class PostCheckoutUseCase implements PostCheckoutInput {
    private final PostCheckoutOutput checkoutOutput;
    private final PaymentGateway paymentGateway;
    private final OrderGateway orderGateway;
    private final TransactionGateway transactionGateway;

    @Inject
    public PostCheckoutUseCase(PostCheckoutOutput checkoutOutput, PaymentGateway paymentGateway, OrderGateway orderGateway, TransactionGateway transactionGateway) {
        this.checkoutOutput = checkoutOutput;
        this.paymentGateway = paymentGateway;
        this.orderGateway = orderGateway;
        this.transactionGateway = transactionGateway;
    }

    @Override
    public void execute(CheckoutRequestModel requestModel) {
        final var errors = new ArrayList<ErrorResponseModel>();

        this.validate(requestModel, errors);

        if (!errors.isEmpty()) {
            this.checkoutOutput.error(errors);
            return;
        }

        final var order = this.orderGateway.create(
                Order.builder()
                        .keyType(KeyType.get(requestModel.getKeyType()))
                        .key(requestModel.getKey())
                        .payer(requestModel.getPayer())
                        .city(requestModel.getCity())
                        .product(requestModel.getProduct())
                        .price(requestModel.getPrice())
                        .build()
        );

        final var transaction = this.transactionGateway.create(
                Transaction.builder()
                        .orderId(order.getId())
                        .status(TransactionStatus.AGUARDANDO)
                        .payer(requestModel.getPayer())
                        .payee("Henrique Dreyer")
                        .value(requestModel.getPrice())
                        .build()
        );

        final var checkout = Checkout.builder()
                .transactionId(transaction.getId())
                .keyType(order.getKeyType())
                .key(order.getKey())
                .payer(order.getPayer())
                .city(order.getCity())
                .product(order.getProduct())
                .price(order.getPrice())
                .build();

        var payment = this.paymentGateway.generatePayment(checkout);

        var responseModel = PaymentMethodResponseModel.builder()
                .value(payment.getValue())
                .transactionId(payment.getTransactionId())
                .build();

        this.checkoutOutput.success(responseModel);
    }

    private void validate(final CheckoutRequestModel requestModel,
                          final ArrayList<ErrorResponseModel> errors) {

        if(Objects.isNull(requestModel.getKeyType()) || !KeyType.isValid(requestModel.getKeyType())) {
            errors.add(ErrorResponseModel.builder()
                    .message("Tipo de chave inválida.")
                    .build());
        }

        if(Objects.isNull(requestModel.getKey()) || requestModel.getKey().isBlank()) {
            errors.add(ErrorResponseModel.builder()
                    .message("Chave invalida.")
                    .build());
        }

        if(Objects.isNull(requestModel.getPayer())) {
            errors.add(ErrorResponseModel.builder()
                    .message("Pagador invalido.")
                    .build());
        }

        if(Objects.isNull(requestModel.getCity())) {
            errors.add(ErrorResponseModel.builder()
                    .message("Cidade invalida.")
                    .build());
        }

        if (requestModel.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.add(ErrorResponseModel.builder()
                    .message("Preço do produto invalido.")
                    .build());
        }

        if (requestModel.getProduct() == null || requestModel.getProduct().isBlank()) {
            errors.add(ErrorResponseModel.builder()
                    .message("Nome do produto invalido.")
                    .build());
        }
    }
}
