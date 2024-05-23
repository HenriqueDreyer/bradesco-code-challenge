package com.dreyer.bradescocodechallenge.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "NOTIFICATION")
public class NotificationEntity {
    private String message;
    private UUID cartId;
}
