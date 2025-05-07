package com.example.demo.entity;

import com.example.demo.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToOne
    @JoinColumn(name = "sku_id", nullable = false)
    private SKU sku;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private SubscriptionStatus subscriptionStatus;

    @OneToOne
    @JoinColumn(name = "paymentMethod_id", nullable = false)
    private PaymentMethod paymentMethod;
}

