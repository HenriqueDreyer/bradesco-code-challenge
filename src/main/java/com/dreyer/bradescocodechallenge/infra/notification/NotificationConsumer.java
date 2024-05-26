package com.dreyer.bradescocodechallenge.infra.notification;

import com.dreyer.bradescocodechallenge.business.domain.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    @KafkaListener(topics = "transaction-notification", groupId = "bradesco-code-challenge")
    public void receiveNotification(Notification notification) {
        log.info("[CONSUMER] Notified by transaction {}", notification.getTransactionId());
        log.info("[CONSUMER] Your Payment has ben {}", notification.getMessage());
    }
}
