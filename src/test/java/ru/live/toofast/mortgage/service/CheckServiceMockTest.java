package ru.live.toofast.mortgage.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.live.toofast.mortgage.model.MortgageList;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("/data.sql")
public class CheckServiceMockTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"Irina\", \"passport\": \"7800 567897\", \"period\": 12, \"salary\": 30000.0, \"creditAmount\": 100000.0}", //New client
            "{ \"name\":\"Oleg\", \"passport\": \"7800 567895\", \"period\": 12, \"salary\": 30000.0, \"creditAmount\": 100000.0}" //GRADE=78.0
    })
    public void checkSuccess(String content) throws Exception {

        MortgageList response = getMortgageList(content);
        Assertions.assertEquals(1,response.getValues().size());
        Assertions.assertEquals("SUCCESS", response.getValues().get(0).getStatus().toString());

    }

    @Test
    public void checkDeclineTerrorist() throws Exception{
        String content="{ \"name\":\"Igor\", \"passport\": \"7800 567890\", \"period\": 12, \"salary\": 30000.0, \"creditAmount\": 100000.0}";
        MortgageList response = getMortgageList(content);
        Assertions.assertEquals("DECLINE",response.getValues().get(0).getStatus().toString());
        Assertions.assertEquals("TERRORIST",response.getValues().get(0).getDeclineReason().toString());

    }

    @Test
    public void checkDeclineScoringFailed() throws Exception{
        String content="{ \"name\":\"Yuliya\", \"passport\": \"7800 567894\", \"period\": 12, \"salary\": 30000.0, \"creditAmount\": 100000.0}";
        MortgageList response = getMortgageList(content);
        Assertions.assertEquals("DECLINE",response.getValues().get(0).getStatus().toString());
        Assertions.assertEquals("SCORING_FAILED",response.getValues().get(0).getDeclineReason().toString());
    }

    @Test
    public void checkDeclineLowSalary() throws Exception{
        String content="{ \"name\":\"Oleg\", \"passport\": \"7800 567895\", \"period\": 12, \"salary\": 30000.0, \"creditAmount\": 200000.0}";
        MortgageList response = getMortgageList(content);
        Assertions.assertEquals("DECLINE",response.getValues().get(0).getStatus().toString());
        Assertions.assertEquals("LOW_SALARY",response.getValues().get(0).getDeclineReason().toString());
    }


    private MortgageList getMortgageList(String content) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/mortgage")
                .content(content)
                .contentType("application/json")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/mortgages"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(contentAsString, MortgageList.class);
    }


}