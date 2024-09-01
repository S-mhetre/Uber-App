package com.sakshi.project.uber.uberApp.strategies.impl;

import com.sakshi.project.uber.uberApp.entities.Driver;
import com.sakshi.project.uber.uberApp.entities.Payment;
import com.sakshi.project.uber.uberApp.entities.Wallet;
import com.sakshi.project.uber.uberApp.entities.enums.TransactionMethod;
import com.sakshi.project.uber.uberApp.services.WalletService;
import com.sakshi.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Rider -> 100
//Driver -> 70 Deduct 30RS from drivers wallet

@Service
@RequiredArgsConstructor
public class CODPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide(), TransactionMethod.RIDE);
    }
}
