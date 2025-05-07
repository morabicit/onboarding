package com.example.demo.entity;

import com.example.demo.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private CardType cardType;

    @Column(nullable = false)
    private String maskedCardNumber;

    @Column(nullable = false)
    private String issuerCountry;

    @Column(nullable = false)
    private String expiryDate;
}
