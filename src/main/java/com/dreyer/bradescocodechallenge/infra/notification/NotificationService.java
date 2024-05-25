package com.dreyer.bradescocodechallenge.infra.notification;

import com.dreyer.bradescocodechallenge.business.domain.entity.Notification;
import com.dreyer.bradescocodechallenge.business.domain.gateway.NotificationGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService implements NotificationGateway {

    @Override
    public void notify(Notification notification) {

    }
}
