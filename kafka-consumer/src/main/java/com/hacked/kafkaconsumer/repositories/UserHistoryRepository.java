package com.hacked.kafkaconsumer.repositories;

import com.hacked.kafkaconsumer.entities.UserHistoryEntity;
import com.hacked.kafkamodel.models.ChangeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistoryEntity, Integer> {
    ChangeEvent findByUserId(int userId);
}
