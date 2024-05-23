package com.dreyer.bradescocodechallenge.infra.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "NOTIFICATION")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @OneToMany
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
}
