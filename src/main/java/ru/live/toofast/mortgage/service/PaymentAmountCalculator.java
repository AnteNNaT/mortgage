package ru.live.toofast.mortgage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentAmountCalculator {

    private final float monthlyPaymentPercentOfSalary= 0.5f;
    Logger logger = LoggerFactory.getLogger(PaymentAmountCalculator.class);

public boolean check(Long period, Long salary, Long creditAmount){
    try{
        return (((float) (creditAmount / period) / salary) <= monthlyPaymentPercentOfSalary);
    } catch (ArithmeticException e) {
        logger.error("Argument 'period' or 'salary' is 0", e);
    }
    return false;
}
}
