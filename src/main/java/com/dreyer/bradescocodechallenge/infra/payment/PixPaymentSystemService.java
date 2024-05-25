package com.dreyer.bradescocodechallenge.infra.payment;

import com.dreyer.bradescocodechallenge.business.domain.entity.*;
import com.dreyer.bradescocodechallenge.business.domain.gateway.PaymentSystemGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Service
public class PixPaymentSystemService implements PaymentSystemGateway {
    private final RestClient restClient;

    @Value("${servico.pix.cobranca}")
    private String geracaoPixBaseUrl;

    @Value("${servico.pix.pagamento}")
    private String pagamentoBaseUrl;

    @Autowired
    public PixPaymentSystemService(RestClient.Builder restClientBuilder) {
        this.restClient = RestClient.builder()
                .baseUrl(geracaoPixBaseUrl)
                .build();
    }

    @Override
    public PaymentMethod generatePayment(Checkout checkout) {
        final var transactionId = checkout.getTransactionId().toString();

        final var requestBody = CheckoutRequestModel.builder()
                .keyType(checkout.getKeyType().getValue())
                .key(checkout.getKey())
                .payer(checkout.getPayer())
                .city(checkout.getCity())
                .value(checkout.getPrice())
                .build();

        log.info("Generating QRCode ...");

        var response = this.restClient.post()
                .uri(geracaoPixBaseUrl + transactionId)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(CheckoutResponseModel.class);

        if (response.getStatusCode().isError()) {
            log.error("Error to generate QRCode Data");
            throw new PixPaymentSystemException("Erro ao gerar pagamento");
        }

        return PaymentMethod.builder()
                .value(response.getBody().getValue())
                .transactionId(response.getBody().getUrl())
                .build();
    }

    @Async
    public CompletableFuture<Payment> realizePayment(Transaction transaction) {
        final var requestBody = PaymentRequestModel.builder()
                .transactionId(transaction.getId())
                .orderId(transaction.getOrderId())
                .value(transaction.getValue())
                .build();

        log.info("Realizing payment ...");

        final var response = this.restClient.post()
                .uri(pagamentoBaseUrl + transaction.getId())
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(PaymentResponseModel.class);

        if(response.getStatusCode().isError()) {
            log.error("Error in Payment");
            return CompletableFuture.failedFuture(new PixPaymentSystemException("Error on realizing payment"));
        }

        final var payment = Payment.builder()
                .status(TransactionStatus.get(response.getBody().getStatus()))
                .build();

        log.info(String.format("Payment order %s: %s", transaction.getOrderId(),
                payment.getStatus()));

        return CompletableFuture.completedFuture(payment);
    }
}
