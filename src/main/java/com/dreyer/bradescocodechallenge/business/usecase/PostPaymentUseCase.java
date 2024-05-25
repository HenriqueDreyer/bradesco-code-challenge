package com.dreyer.bradescocodechallenge.business.usecase;

import com.dreyer.bradescocodechallenge.business.boundary.input.PostPaymentInput;
import com.dreyer.bradescocodechallenge.business.boundary.output.PostPaymentOutput;
import com.dreyer.bradescocodechallenge.business.domain.entity.Payment;
import com.dreyer.bradescocodechallenge.business.domain.gateway.NotificationGateway;
import com.dreyer.bradescocodechallenge.business.domain.gateway.PaymentSystemGateway;
import com.dreyer.bradescocodechallenge.business.domain.gateway.TransactionGateway;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;
import com.dreyer.bradescocodechallenge.infra.payment.PaymentRequestModel;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Named
public class PostPaymentUseCase implements PostPaymentInput {
    private final PaymentSystemGateway paymentSystemGateway;
    private final TransactionGateway transactionGateway;
    private final NotificationGateway notificationGateway;
    private final PostPaymentOutput postPaymentOutput;

    @Inject
    public PostPaymentUseCase(PaymentSystemGateway paymentSystemGateway, TransactionGateway transactionGateway, NotificationGateway notificationGateway, PostPaymentOutput postPaymentOutput) {
        this.paymentSystemGateway = paymentSystemGateway;
        this.transactionGateway = transactionGateway;
        this.notificationGateway = notificationGateway;
        this.postPaymentOutput = postPaymentOutput;
    }

    @Override
    public void execute(PaymentRequestModel requestModel) {
        final var errors = new ArrayList<ErrorResponseModel>();
        this.validate(requestModel);

        if (!errors.isEmpty()) {
            this.postPaymentOutput.error(errors);
            return;
        }

        final var transaction = this.transactionGateway.findById(requestModel.getTransactionId());

        if (Objects.isNull(transaction)) {
            errors.add(ErrorResponseModel.builder()
                    .message(String.format("Transaction id: %s invalid.", requestModel.getTransactionId()))
                    .build());

            this.postPaymentOutput.error(errors);
            return;
        }

        final CompletableFuture<Payment> paymentFuture = CompletableFuture.supplyAsync(() -> {
            // TODO: NOTIFICACAO AQUI
            this.paymentSystemGateway.realizePayment(transaction);
        });

    }
}
