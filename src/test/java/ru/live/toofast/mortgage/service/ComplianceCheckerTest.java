package ru.live.toofast.mortgage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Sql("/data.sql")
class ComplianceCheckerTest {

    @Autowired
    ComplianceChecker complianceChecker;


    @Test
    void checkComplianceChecker() {

    boolean isNotTerrorist1 = complianceChecker.check("7800 567892");
        boolean isNotTerrorist2 = complianceChecker.check("7800 567890");

       Assertions.assertTrue(isNotTerrorist1);
        Assertions.assertFalse(isNotTerrorist2);
    }

}