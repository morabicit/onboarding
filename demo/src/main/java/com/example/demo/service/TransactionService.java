package com.example.demo.service;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.SKU;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repository.SKURepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.utils.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final TransactionMapper transactionMapper;
    public TransactionService( TransactionRepository transactionRepository,UserService userService,
                               TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.transactionMapper = transactionMapper;
    }
    public List<TransactionDto> getAllTransactionsPerUser() {
        User user = userService.getCurrentUser();
        List<Transaction> transactions =  transactionRepository.findAllByUser(user);
        return transactions.stream().map(transactionMapper::toDto).collect(Collectors.toList());

    }
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(transactionMapper::toDto).collect(Collectors.toList());
    }
}
