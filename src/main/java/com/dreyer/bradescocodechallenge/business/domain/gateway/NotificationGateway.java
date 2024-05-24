package com.dreyer.bradescocodechallenge.business.domain.gateway;

import com.dreyer.bradescocodechallenge.business.domain.entity.Notification;

public interface NotificationGateway {
    void notify(Notification notification);
}
