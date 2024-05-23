package com.dreyer.bradescocodechallenge.infra.jpa.repository;

import com.dreyer.bradescocodechallenge.infra.jpa.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Long> {
}
