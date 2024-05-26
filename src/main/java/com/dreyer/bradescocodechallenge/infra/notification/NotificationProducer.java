package com.dreyer.bradescocodechallenge.infra.notification;

import com.dreyer.bradescocodechallenge.business.domain.entity.Notification;
import com.dreyer.bradescocodechallenge.business.domain.gateway.NotificationGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class NotificationProducer implements NotificationGateway {
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    @Autowired
    public NotificationProducer(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<Boolean> notify(Notification notification) {
        log.info("[NOTIFICATION] Notifying Consumers ...");
        kafkaTemplate.send("transaction-notification", notification);
        return CompletableFuture.completedFuture(true);
    }
}
