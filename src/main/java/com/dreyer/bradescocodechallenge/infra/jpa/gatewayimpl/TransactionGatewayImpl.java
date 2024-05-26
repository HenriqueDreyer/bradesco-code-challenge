package com.dreyer.bradescocodechallenge.infra.jpa.gatewayimpl;

import com.dreyer.bradescocodechallenge.business.domain.entity.Transaction;
import com.dreyer.bradescocodechallenge.business.domain.entity.TransactionStatus;
import com.dreyer.bradescocodechallenge.business.domain.gateway.TransactionGateway;
import com.dreyer.bradescocodechallenge.infra.jpa.entity.OrderEntity;
import com.dreyer.bradescocodechallenge.infra.jpa.entity.TransactionEntity;
import com.dreyer.bradescocodechallenge.infra.jpa.repository.TransactionEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionGatewayImpl implements TransactionGateway {
    private final TransactionEntityRepository transactionEntityRepository;

    @Autowired
    public TransactionGatewayImpl(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction create(Transaction transaction) {
        final var entity = TransactionEntity.builder()
                .orderEntity(OrderEntity.builder()
                        .id(transaction.getOrderId())
                        .build())
                .status(transaction.getStatus().getValue())
                .payer(transaction.getPayer())
                .payee(transaction.getPayee())
                .value(transaction.getValue())
                .build();

        final var saved = this.transactionEntityRepository.save(entity);

        return Transaction.builder()
                .id(saved.getId())
                .orderId(saved.getOrderEntity().getId())
                .status(transaction.getStatus())
                .payer(transaction.getPayer())
                .payee(transaction.getPayee())
                .value(transaction.getValue())
                .build();
    }

    @Override
    public Transaction findById(Long id) {
        final var entity = transactionEntityRepository.findById(id)
                .orElse(null);

        if(entity == null) return null;

        return Transaction.builder()
                .id(entity.getId())
                .orderId(entity.getOrderEntity().getId())
                .status(TransactionStatus.get(entity.getStatus()))
                .payee(entity.getPayee())
                .payer(entity.getPayer())
                .value(entity.getValue())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public Transaction updateStatus(Transaction transaction) {
        final var entity = this.transactionEntityRepository.findById(transaction.getId()).get();
        entity.setStatus(transaction.getStatus().getValue());

        this.transactionEntityRepository.save(entity);

        return Transaction.builder()
                .id(entity.getId())
                .orderId(entity.getOrderEntity().getId())
                .status(transaction.getStatus())
                .payer(entity.getPayer())
                .payee(entity.getPayee())
                .value(entity.getValue())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
