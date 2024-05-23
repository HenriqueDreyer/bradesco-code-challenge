package com.dreyer.bradescocodechallenge.business.gateway;

import com.dreyer.bradescocodechallenge.business.entity.Transaction;

public interface TransactionGateway {
    Transaction create(Transaction transaction);
}
