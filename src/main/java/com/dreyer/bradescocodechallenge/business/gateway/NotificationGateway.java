package com.dreyer.bradescocodechallenge.business.gateway;

import com.dreyer.bradescocodechallenge.business.entity.Notification;

public interface NotificationGateway {
    void send(Notification notification);
}
