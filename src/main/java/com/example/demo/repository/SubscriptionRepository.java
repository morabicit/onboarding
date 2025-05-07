package com.example.demo.repository;

import com.example.demo.entity.Subscription;
import com.example.demo.entity.User;
import com.example.demo.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
    Subscription findByUser(User user);

    Optional<Subscription> findByUserAndSubscriptionStatus(User user, SubscriptionStatus subscriptionStatus);
}
