package com.sakshi.project.uber.uberApp.services;

import com.sakshi.project.uber.uberApp.entities.Payment;
import com.sakshi.project.uber.uberApp.entities.Ride;
import com.sakshi.project.uber.uberApp.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment creatNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
