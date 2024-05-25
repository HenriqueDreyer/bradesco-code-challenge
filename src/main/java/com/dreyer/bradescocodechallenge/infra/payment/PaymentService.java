package com.dreyer.bradescocodechallenge.infra.payment;

import com.dreyer.bradescocodechallenge.business.domain.entity.Checkout;
import com.dreyer.bradescocodechallenge.business.domain.entity.Payment;
import com.dreyer.bradescocodechallenge.business.domain.gateway.PaymentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class PaymentService implements PaymentGateway {
    private final RestClient restClient;

    @Value("${servico.pix.cobranca}")
    private String geracaoPixBaseUrl;

    @Autowired
    public PaymentService(RestClient.Builder restClientBuilder) {
        this.restClient = RestClient.builder()
                .baseUrl(geracaoPixBaseUrl)
                .build();
    }

    @Override
    public Payment generatePayment(Checkout checkout) {
        final var transactionId = checkout.getTransactionId().toString();

        final var requestBody = PaymentRequestModel.builder()
                .keyType(checkout.getKeyType().getValue())
                .key(checkout.getKey())
                .payer(checkout.getPayer())
                .city(checkout.getCity())
                .value(checkout.getPrice())
                .build();

        log.info("Generating QRCode");

        var response = this.restClient.post()
                .uri(geracaoPixBaseUrl + transactionId)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(PaymentResponseModel.class);

        if (response.getStatusCode().isError()) {
            throw new PaymentException("Erro ao gerar pagamento");
        }

        return Payment.builder()
                .value(response.getBody().getValue())
                .transactionId(response.getBody().getUrl())
                .build();
    }
}
