package com.dreyer.bradescocodechallenge.infra.jpa.gatewayimpl;

import com.dreyer.bradescocodechallenge.business.domain.entity.Transaction;
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
}
