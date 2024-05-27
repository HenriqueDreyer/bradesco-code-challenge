package com.dreyer.bradescocodechallenge.infra.payment;

import com.dreyer.bradescocodechallenge.business.domain.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@EnableAsync
@ExtendWith(MockitoExtension.class)
class PixPaymentSystemServiceTest {

    @Autowired
    @InjectMocks
    PixPaymentSystemService pixPaymentSystemService;

    @SpyBean
    RestClient.Builder builder;

    @Autowired
    private ObjectMapper objectMapper;

    private String geracaoPixBaseUrl = "https://bradesco-code-challenge.wiremockapi.cloud/api/cob/";

    private String pagamentoBaseUrl = "https://bradesco-code-challenge.wiremockapi.cloud/api/pag/";

    private MockRestServiceServer mockServer;

    private Long transactionId = Long.MAX_VALUE;
    private KeyType keyType = KeyType.PHONE;
    private String key = "81998101041";
    private String payer = "Helenna";
    private String city = "Recife";
    private String product = "Camisa do Sport 1-4 Fortaleza";
    private BigDecimal price = new BigDecimal(9.99).setScale(2, RoundingMode.HALF_DOWN);

    @BeforeEach
    public void setup() {
        this.pixPaymentSystemService = new PixPaymentSystemService(geracaoPixBaseUrl, pagamentoBaseUrl, RestClient.builder());
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void generatePayment() throws JsonProcessingException {
        final var checkout = Checkout.builder()
                .transactionId(transactionId)
                .keyType(keyType)
                .key(key)
                .payer(payer)
                .city(city)
                .product(product)
                .price(price)
                .build();

        final var requestBody = CheckoutRequestBody.builder()
                .keyType(checkout.getKeyType().getValue())
                .key(checkout.getKey())
                .payer(checkout.getPayer())
                .city(checkout.getCity())
                .value(checkout.getPrice())
                .build();

        final var expectedResult = PaymentMethod.builder()
                .value(price.toString())
                .transactionId(transactionId.toString())
                .build();

        final var json = objectMapper.writeValueAsString(requestBody);

        this.builder = RestClient.builder().baseUrl(geracaoPixBaseUrl);
        this.mockServer = MockRestServiceServer.bindTo(builder).build();

        final var transactionId = checkout.getTransactionId().toString();
        mockServer.expect(requestTo(geracaoPixBaseUrl + transactionId))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(json))
                .andExpect(header("Content-Type", "application/json"))
                .andRespond(withSuccess());

        PaymentMethod respEntity = this.pixPaymentSystemService.generatePayment(checkout);
//        mockServer.verify();
        assertThat(respEntity.getValue()).isNotNull();
        assertThat(respEntity.getTransactionId()).isNotNull();
    }

    @Test
    void realizePayment() throws JsonProcessingException {
        final var transaction = Transaction.builder()
                .id(Long.MAX_VALUE)
                .orderId(Long.MAX_VALUE)
                .status(TransactionStatus.AGUARDANDO)
                .payee("Henrique")
                .payer("Helenna")
                .value(price)
                .createdAt(LocalDateTime.now())
                .build();

        final var requestBody = PaymentRequestBody.builder()
                .transactionId(transaction.getId())
                .value(transaction.getValue())
                .build();
        final var json = objectMapper.writeValueAsString(requestBody);

        this.builder = RestClient.builder().baseUrl(pagamentoBaseUrl);
        this.mockServer = MockRestServiceServer.bindTo(builder).build();

        mockServer.expect(requestTo(pagamentoBaseUrl + transaction.getId()))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(json))
                .andExpect(header("Content-Type", "application/json"))
                .andRespond(withSuccess());

        final var payment = Payment.builder()
                .transactionId(transaction.getId())
                .status(TransactionStatus.REALIZADO)
                .build();
        CompletableFuture<Payment> paymentCompletableFuture = CompletableFuture.completedFuture(payment);

        final var feature = this.pixPaymentSystemService.realizePayment(transaction);
        feature.thenAccept(p -> {
            assertThat(p.getTransactionId()).isEqualTo(transaction.getId());
        });

    }
}