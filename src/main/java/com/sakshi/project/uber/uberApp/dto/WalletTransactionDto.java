package com.sakshi.project.uber.uberApp.dto;

import com.sakshi.project.uber.uberApp.entities.Ride;
import com.sakshi.project.uber.uberApp.entities.Wallet;
import com.sakshi.project.uber.uberApp.entities.enums.TransactionMethod;
import com.sakshi.project.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionDto {

    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto ride;

    private String transactionId;

    @ManyToOne
    private WalletDto walletDto;

    @CreationTimestamp
    private LocalDateTime timeStamp;
}
