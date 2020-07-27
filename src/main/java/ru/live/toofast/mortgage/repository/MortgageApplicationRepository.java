package ru.live.toofast.mortgage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.live.toofast.mortgage.entity.ApplicationStatus;
import ru.live.toofast.mortgage.entity.MortgageApplication;

import java.util.List;

public interface MortgageApplicationRepository extends JpaRepository<MortgageApplication, Long> {

    List<MortgageApplication> findAllByStatusEquals(ApplicationStatus status);
}
