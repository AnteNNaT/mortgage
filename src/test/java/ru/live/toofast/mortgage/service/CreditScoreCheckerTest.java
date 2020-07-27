package ru.live.toofast.mortgage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Sql("/data.sql")
class CreditScoreCheckerTest {

    @Autowired
    CreditScoreChecker creditScoreChecker;

    @DisplayName("Checking credit score by passport number")
    @ParameterizedTest
    @ValueSource(strings = {"7800 567893", "7800 567894"})
    void checkFalseCreditScoreChecker(String passportId) {

        boolean isSufficient = creditScoreChecker.check(passportId);
        Assertions.assertFalse(isSufficient);

    }

    @DisplayName("Checking credit score by passport number")
    @ParameterizedTest
    @ValueSource(strings = {"7800 567896", "7800 567895"})
    void checkTrueCreditScoreChecker(String passportId) {

        boolean isSufficient = creditScoreChecker.check(passportId);
        Assertions.assertTrue(isSufficient);
    }

}