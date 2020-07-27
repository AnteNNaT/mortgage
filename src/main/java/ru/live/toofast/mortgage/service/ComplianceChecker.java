package ru.live.toofast.mortgage.service;

import org.springframework.stereotype.Service;
import ru.live.toofast.mortgage.repository.BadPersonsRepository;

@Service
public class ComplianceChecker {

    private final BadPersonsRepository repository;

    public ComplianceChecker(BadPersonsRepository repository) {
        this.repository = repository;
    }

    public boolean check(String passportId) {
       return (repository.findFirstByPassportId(passportId)==null);
       }
    }

