package com.dreyer.bradescocodechallenge.infra.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "NOTIFICATION")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    private String transaction;
}
