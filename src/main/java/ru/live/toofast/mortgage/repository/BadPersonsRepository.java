package ru.live.toofast.mortgage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.live.toofast.mortgage.entity.BadPersons;

public interface BadPersonsRepository extends JpaRepository<BadPersons, Long> {

     BadPersons findFirstByPassportId(String passportId);

}
