package ru.live.toofast.mortgage.service;

import org.springframework.stereotype.Service;
import ru.live.toofast.mortgage.repository.CreditScoreRepository;

@Service
public class CreditScoreChecker {

private final CreditScoreRepository repository;
private final double sufficientCreditScore=75.0;

    public CreditScoreChecker(CreditScoreRepository repository) {
        this.repository = repository;
    }

    public boolean check(String passportId) {
        return ((repository.findFirstByPassportId(passportId)==null)||
                (repository.findFirstByPassportId(passportId).getGrade()>sufficientCreditScore));
        }

    }
