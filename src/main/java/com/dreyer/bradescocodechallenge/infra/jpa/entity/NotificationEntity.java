package com.dreyer.bradescocodechallenge.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "NOTIFICACAO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MENSAGEM")
    private String message;

    @Column(name = "CODIGO_TRANSACAO")
    private TransactionEntity transaction;
}
