package com.dreyer.bradescocodechallenge.infra.notification;

import com.dreyer.bradescocodechallenge.business.domain.entity.Notification;
import com.dreyer.bradescocodechallenge.business.domain.gateway.NotificationGateway;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements NotificationGateway {
    @Override
    public void notify(Notification notification) {

    }
}
