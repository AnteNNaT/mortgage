package ru.live.toofast.mortgage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentAmountCalculatorTest {

    @Autowired
    PaymentAmountCalculator paymentAmountCalculator;

    @Test
    void checkPaymentAmountCalculator() {

        Assertions.assertTrue(paymentAmountCalculator.check(12L, 30000L, 100000L));
        Assertions.assertTrue(paymentAmountCalculator.check(10L, 30000L, 150000L));
        Assertions.assertFalse(paymentAmountCalculator.check(12L, 30000L, 200000L));
        Assertions.assertFalse(paymentAmountCalculator.check(0L, 30000L, 200000L));


    }
}