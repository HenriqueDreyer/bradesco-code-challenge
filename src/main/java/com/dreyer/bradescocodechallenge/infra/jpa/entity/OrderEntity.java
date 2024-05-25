package com.dreyer.bradescocodechallenge.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "COMPRA")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(mappedBy = "orderEntity")
    private TransactionEntity transactionId;

    @Column(name = "TIPO_CHAVE")
    private int keyType;

    @Column(name = "CHAVE")
    private String userKey;

    @Column(name = "PAGADOR")
    private String payer;

    @Column(name = "CIDADE")
    private String city;

    @Column(name = "NOME_PRODUTO", nullable = false)
    private String product;

    @Column(name = "PRECO", nullable = false)
    private BigDecimal price;
}
