package ru.live.toofast.mortgage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.live.toofast.mortgage.model.MortgageRequest;


@SpringBootTest
@Transactional
@Sql("/data.sql")
class CheckServiceTest {

    @Autowired
    CheckService checkService;

    @Test
    void checkCheckService(){
        MortgageRequest request=new MortgageRequest();
        request.setName("Igor");
        request.setPassport("7800 567894"); //GRADE=45.0
        request.setPeriod(12L);
        request.setSalary(30000L);
        request.setCreditAmount(100000L);

        MortgageRequest request2=new MortgageRequest();
        request2.setName("Oleg");
        request2.setPassport("7800 567890"); //TERRORIST
        request2.setPeriod(12L);
        request2.setSalary(30000L);
        request2.setCreditAmount(100000L);

        MortgageRequest request3=new MortgageRequest();
        request3.setName("Anna");
        request3.setPassport("7800 567895");
        request3.setPeriod(12L);
        request3.setSalary(30000L);
        request3.setCreditAmount(200000L); //LOW_SALARY

        Assertions.assertEquals("DECLINE",checkService.status(request).toString());
        Assertions.assertEquals("SCORING_FAILED",checkService.declineReason(request).get().toString());

        Assertions.assertEquals("DECLINE",checkService.status(request2).toString());
        Assertions.assertEquals("TERRORIST",checkService.declineReason(request2).get().toString());

        Assertions.assertEquals("DECLINE",checkService.status(request3).toString());
        Assertions.assertEquals("LOW_SALARY",checkService.declineReason(request3).get().toString());

        request3.setCreditAmount(100000L);
        Assertions.assertEquals("SUCCESS",checkService.status(request3).toString());

    }

}