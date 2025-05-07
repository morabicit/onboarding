package com.example.demo.service;

import com.example.demo.dto.PaymentMethodDto;
import com.example.demo.entity.PaymentMethod;
import com.example.demo.entity.User;
import com.example.demo.enums.CardType;
import com.example.demo.exception.InvalidPaymentMethod;
import com.example.demo.repository.PaymentMethodRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.PaymentMethodMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.utils.CardUtils.*;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final UserService userService;
    private final PaymentMethodMapper paymentMethodMapper;

    public PaymentMethodService(UserRepository userRepository, PaymentMethodRepository paymentMethodRepository, UserService userService, PaymentMethodMapper paymentMethodMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.userService = userService;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    public PaymentMethod addPaymentMethod(PaymentMethodDto pmDto) {
        // Validate card number (e.g., Luhn algorithm)
        if(!validateCardNumber(pmDto.getCardNumber())){
            throw new InvalidPaymentMethod("invalid card number");
        }
        if(!isValidExpiryDate(pmDto.getExpiryDate())){
            throw new InvalidPaymentMethod("invalid expiry date");
        }
        // Mask the card number
        String maskedCardNumber = maskCardNumber(pmDto.getCardNumber());

        // Optional: Encrypt full card number (if needed for future use)
        //String encryptedCardNumber = encryptCardNumber(request.getCardNumber());

        User user = userService.getCurrentUser();
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setUser(user);
        paymentMethod.setCardType(CardType.valueOf(pmDto.getCardType().toUpperCase()));
        paymentMethod.setMaskedCardNumber(maskedCardNumber);
        paymentMethod.setExpiryDate(pmDto.getExpiryDate());
        paymentMethod.setIssuerCountry(pmDto.getIssuerCountry());
        paymentMethodRepository.save(paymentMethod);

        return paymentMethod;
    }

    public List<PaymentMethodDto> getUserPaymentMethods() {
        User user = userService.getCurrentUser();
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAllByUser(user);
        return paymentMethods.stream().map(paymentMethodMapper::toDto).collect(Collectors.toList());
    }
}

