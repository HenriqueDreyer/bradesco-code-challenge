package com.dreyer.bradescocodechallenge.infra.jpa.repository;

import com.dreyer.bradescocodechallenge.infra.jpa.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {
}
