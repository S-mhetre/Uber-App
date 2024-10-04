package com.sakshi.project.uber.uberApp.services;


import com.sakshi.project.uber.uberApp.dto.WalletTransactionDto;
import com.sakshi.project.uber.uberApp.entities.WalletTransaction;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
