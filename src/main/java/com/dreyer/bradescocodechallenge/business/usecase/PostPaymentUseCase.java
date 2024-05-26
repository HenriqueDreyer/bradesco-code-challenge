package com.dreyer.bradescocodechallenge.business.usecase;

import com.dreyer.bradescocodechallenge.business.boundary.input.PostPaymentInput;
import com.dreyer.bradescocodechallenge.business.boundary.output.PostPaymentOutput;
import com.dreyer.bradescocodechallenge.business.boundary.requestmodel.PaymentRequestModel;
import com.dreyer.bradescocodechallenge.business.boundary.responsemodel.PaymentResponseModel;
import com.dreyer.bradescocodechallenge.business.domain.entity.Notification;
import com.dreyer.bradescocodechallenge.business.domain.entity.Payment;
import com.dreyer.bradescocodechallenge.business.domain.entity.Transaction;
import com.dreyer.bradescocodechallenge.business.domain.entity.TransactionStatus;
import com.dreyer.bradescocodechallenge.business.domain.gateway.NotificationGateway;
import com.dreyer.bradescocodechallenge.business.domain.gateway.PaymentSystemGateway;
import com.dreyer.bradescocodechallenge.business.domain.gateway.TransactionGateway;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
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
        try {
            final var errors = new ArrayList<ErrorResponseModel>();

            if (Objects.isNull(requestModel.getTransactionId())) {
                errors.add(ErrorResponseModel.builder()
                        .message("TransactionId required.")
                        .build());
            }

            final var transaction = this.transactionGateway.findById(requestModel.getTransactionId());

            if (Objects.isNull(transaction)) {
                errors.add(ErrorResponseModel.builder()
                        .message(String.format("Transaction id: %s invalid.", requestModel.getTransactionId()))
                        .build());
            }

            if (!errors.isEmpty()) {
                this.postPaymentOutput.error(errors);
                return;
            }

            CompletableFuture<Payment> paymentFuture = this.paymentSystemGateway.realizePayment(transaction);
            paymentFuture.thenAccept(payment -> {
                this.transactionGateway.updateStatus(Transaction.builder()
                        .id(payment.getTransactionId())
                        .status(payment.getStatus())
                        .build());
            });
            paymentFuture.thenAccept(payment -> {
                this.notificationGateway.notify(Notification.builder()
                        .transactionId(payment.getTransactionId())
                        .orderId(payment.getOrderId())
                        .message(payment.getStatus().toString())
                        .build());
            });

            this.postPaymentOutput.success(PaymentResponseModel.builder()
                    .transactionId(transaction.getId())
                    .orderId(transaction.getOrderId())
                    .status(TransactionStatus.EM_ANALISE.toString())
                    .build());

        } catch (Exception e) {
            log.error("[ERROR] {}", e.getMessage());
            this.postPaymentOutput.error(List.of(ErrorResponseModel.builder()
                    .message(e.getMessage())
                    .build()));
        }
    }

    private void validate(PaymentRequestModel requestModel, ArrayList<ErrorResponseModel> errors) {

    }
}
