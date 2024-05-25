package com.dreyer.bradescocodechallenge.business.domain.gateway;

import com.dreyer.bradescocodechallenge.business.domain.entity.Transaction;

public interface TransactionGateway {
    Transaction create(Transaction transaction);
    Transaction findById(Long id);
}
