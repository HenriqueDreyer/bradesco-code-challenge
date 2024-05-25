package com.dreyer.bradescocodechallenge.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Table(name = "TRANSACAO")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "COMPRA_ID", referencedColumnName = "id")
    private OrderEntity orderEntity;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "PAGADOR")
    private String payer;

    @Column(name = "RECEBEDOR")
    private String payee;

    @Column(name = "VALOR", nullable = false)
    private BigDecimal value;

    @Column(name = "DATA_CRIACAO")
    @CreatedDate
    private LocalDateTime createdAt;
}
