package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.enums.SubscriptionStatus;
import com.example.demo.exception.InvalidPaymentMethod;
import com.example.demo.exception.SubscriptionException;
import com.example.demo.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final SKURepository skuRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserService userService, SKURepository skuRepository, TransactionRepository transactionRepository, PaymentMethodRepository paymentMethodRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.skuRepository = skuRepository;
        this.transactionRepository = transactionRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<Subscription> getSubPerUser() {
        User user = userService.getCurrentUser();
        Optional<List<Subscription>> subscriptions = subscriptionRepository.findAllByUser(user);
        if (subscriptions.isEmpty()) {
            throw new SubscriptionException("User not subscribed or cancelled");
        }
        return subscriptions.get();
    }
    public Optional<Subscription> getActiveSubPerUser() {
        User user = userService.getCurrentUser();
        Optional<Subscription> subscription = subscriptionRepository.findByUserAndSubscriptionStatus(user, SubscriptionStatus.Active);
        /*if (subscription.isEmpty()) {
            throw new SubscriptionException("User not subscribed or cancelled");
        }*/
        return subscription;
    }

    public String subscribe(Long skuId, Long paymentMethodId) {
        User user = userService.getCurrentUser();
        Optional<Subscription> existingSubscription = getActiveSubPerUser();
        if (!existingSubscription.isEmpty()) {
            throw new SubscriptionException("User Already Subscribed");
        }
        SKU sku = skuRepository.findById(skuId)
                .orElseThrow(() -> new SubscriptionException("Invalid Product"));
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new InvalidPaymentMethod("Invalid Payment Method"));
        try {
            Subscription subscription = new Subscription();
            subscription.setUser(user);
            subscription.setSku(sku);
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusMonths(1).minusDays(1));
            subscription.setSubscriptionStatus(SubscriptionStatus.Active);
            subscription.setPaymentMethod(paymentMethod);
            subscriptionRepository.save(subscription);
            try {
                Transaction transaction = new Transaction();
                transaction.setUser(user);
                transaction.setSubscription(subscription);
                transaction.setAmount(sku.getPrice());
                transaction.setTransactionDate(LocalDateTime.now());
                transaction.setDescription("Subscription for SKU: " + sku.getName());
                transaction.setPaymentMethod(paymentMethod);
                transactionRepository.save(transaction);
            } catch (Exception logEx) {
                System.err.println("Failed to save transaction log: " + logEx.getMessage());
            }
            return "Subscribed successfully";
        } catch (Exception e) {
            throw new SubscriptionException("User unable to subscribe");
        }
    }

    public String update(Long skuId) {
        try {
            User user = userService.getCurrentUser();
            SKU sku = skuRepository.findById(skuId)
                    .orElseThrow(() -> new SubscriptionException("Invalid Product"));
            Subscription subscription = subscriptionRepository.findByUser(user);
            subscription.setSku(sku);
            subscriptionRepository.save(subscription);
            return "Subscription updated successfully";
        } catch (Exception e) {
            throw new SubscriptionException("User unable to update subscribe");
        }
    }

    public void cancel() {

        User user = userService.getCurrentUser();
        Subscription subscription = subscriptionRepository.findByUser(user);
        if (subscription == null) {
            throw new SubscriptionException("User not Subscribed");
        }
        if (subscription.getSubscriptionStatus() == SubscriptionStatus.Canceled) {
            throw new SubscriptionException("Subscription is already canceled");
        }

        subscription.setSubscriptionStatus(SubscriptionStatus.Canceled);
        subscriptionRepository.save(subscription);
    }
}
