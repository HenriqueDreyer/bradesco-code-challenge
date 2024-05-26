package com.dreyer.bradescocodechallenge.business.domain.gateway;

import com.dreyer.bradescocodechallenge.business.domain.entity.Notification;

import java.util.concurrent.CompletableFuture;

public interface NotificationGateway {
    CompletableFuture<Boolean> notify(Notification notification);
}
