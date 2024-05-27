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

@Slf4j
@Service
public class PixPaymentSystemService implements PaymentSystemGateway {
    private final String cobBaseUrl;
    private String pagBaseUrl;
    private RestClient.Builder builder;

    @Autowired
    public PixPaymentSystemService(@Value("${servico.pix.cobranca}") String cobBaseUrl,
                                   @Value("${servico.pix.pagamento}") String pagBaseUrl,
                                   RestClient.Builder builder) {
        this.cobBaseUrl = cobBaseUrl;
        this.pagBaseUrl = pagBaseUrl;
        this.builder = builder;
    }

    @Override
    public PaymentMethod generatePayment(Checkout checkout) {
        final var transactionId = checkout.getTransactionId().toString();

        final var requestBody = CheckoutRequestBody.builder()
                .keyType(checkout.getKeyType().getValue())
                .key(checkout.getKey())
                .payer(checkout.getPayer())
                .city(checkout.getCity())
                .value(checkout.getPrice())
                .build();

        RestClient restClient = builder.baseUrl(cobBaseUrl)
                .build();

        log.info("[QRCODE] Generating QRCode ...");

        var response = restClient.post()
                .uri(cobBaseUrl + transactionId)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(CheckoutResponseBody.class);

        if (response.getStatusCode().isError()) {
            log.error("[QRCODE] Error to generate QRCode Data");
            throw new PixPaymentSystemException("Erro ao gerar pagamento");
        }

        log.info("[QRCODE] QRCode Generated");
        return PaymentMethod.builder()
                .value(response.getBody().getValue())
                .transactionId(response.getBody().getUrl())
                .build();
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<Payment> realizePayment(Transaction transaction) {
        final var requestBody = PaymentRequestBody.builder()
                .transactionId(transaction.getId())
                .value(transaction.getValue())
                .build();

        RestClient restClient = builder.baseUrl(pagBaseUrl)
                .build();

        log.info("[PAYMENT] Processando Pagamento.");

        final var response = restClient.post()
                .uri(pagBaseUrl + transaction.getId())
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(PaymentResponseBody.class);

        if(response.getStatusCode().isError()) {
            log.error("[PAYMENT] Error in Payment Server");
            throw new PixPaymentSystemException("[PAYMENT] Error in Payment Server");
        }

        final var payment = Payment.builder()
                .transactionId(transaction.getId())
                .status(TransactionStatus.get(response.getBody().getStatus()))
                .build();

        log.info("[PAYMENT] Processo Pagamento Finalizado.");
        log.info("[PAYMENT] Payment Transaction {}: {}", transaction.getId(),
                payment.getStatus());

        return CompletableFuture.completedFuture(payment);
    }
}
