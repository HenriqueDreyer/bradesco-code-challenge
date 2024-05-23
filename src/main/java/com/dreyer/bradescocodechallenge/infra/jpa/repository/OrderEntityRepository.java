package com.dreyer.bradescocodechallenge.infra.jpa.repository;

import com.dreyer.bradescocodechallenge.infra.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
}
