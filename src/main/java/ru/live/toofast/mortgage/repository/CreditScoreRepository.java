package ru.live.toofast.mortgage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.live.toofast.mortgage.entity.CreditScore;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {

    CreditScore findFirstByPassportId(String passportId);
}
