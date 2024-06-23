package com.my.code.rule_engine.discountRuleEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.my.code.rule_engine.RuleEngineApplication;
import com.my.code.rule_engine.knowledgeBase.dto.Rule;
import com.my.code.rule_engine.knowledgeBase.service.KnowledgeBaseService;
import com.my.code.rule_engine.ruleEngine.discountRuleEngine.DiscountInfo;
import com.my.code.rule_engine.ruleEngine.discountRuleEngine.UserInfo;
import com.my.code.rule_engine.ruleEngine.discountRuleEngine.UserType;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuleEngineApplication.class)
public class DiscountRuleEngineTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private KnowledgeBaseService knowledgeBaseServiceMock;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(knowledgeBaseServiceMock.getAllRules()).thenReturn(getListOfRules());
    }

    @Test
    public void verifyEmployeeBasedDiscountRule() throws Exception {
        UserInfo userInfo = new UserInfo("Saman", UserType.EMPLOYEE, LocalDate.now(), false, new BigDecimal(1000));

        MvcResult mvcResult = mockMvc.perform(post("/discounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userInfo)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setEmpDiscountRate(new BigDecimal(30));
        discountInfo.setNetBillAmount(new BigDecimal("650.00"));
        discountInfo.setFiveDollarApplicable(true);

        String expectedResponseBody = objectMapper.writeValueAsString(discountInfo);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void verifyAffiliateBasedDiscountRule() throws Exception {
        UserInfo userInfo = new UserInfo("Saman", UserType.AFFILIATE, LocalDate.now(), false, new BigDecimal(1000));

        MvcResult mvcResult = mockMvc.perform(post("/discounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userInfo)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setAffiliateDiscountRate(new BigDecimal(10));
        discountInfo.setNetBillAmount(new BigDecimal("850.00"));
        discountInfo.setFiveDollarApplicable(true);

        String expectedResponseBody = objectMapper.writeValueAsString(discountInfo);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void verifyRegistrationBasedDiscountRule() throws Exception {
        UserInfo userInfo = new UserInfo("Saman", UserType.CUSTOMER, LocalDate.of(1990, 5, 14), false, new BigDecimal(1000));
        MvcResult mvcResult = mockMvc.perform(post("/discounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userInfo)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setRegistrationDiscountRage(new BigDecimal(5));
        discountInfo.setNetBillAmount(new BigDecimal("900.00"));
        discountInfo.setFiveDollarApplicable(true);

        String expectedResponseBody = objectMapper.writeValueAsString(discountInfo);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    @Test
    public void verifyFiveDollarBasedDiscountRule() throws Exception {
        UserInfo userInfo = new UserInfo("Saman", UserType.CUSTOMER, LocalDate.now(), true, new BigDecimal(990));
        MvcResult mvcResult = mockMvc.perform(post("/discounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userInfo)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setFiveDollarApplicable(true);
        discountInfo.setNetBillAmount(new BigDecimal("945.00"));

        String expectedResponseBody = objectMapper.writeValueAsString(discountInfo);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
    }

    private List<Rule> getListOfRules(){
        Rule rule1 = new Rule();
        rule1.setRuleName("employee_discount");
        rule1.setCondition("input.userType == 'EMPLOYEE' && input.isGrocery == false");
        rule1.setAction("output.setEmpDiscountRate(30)");
        rule1.setDescription("employee based discount");

        Rule rule2 = new Rule();
        rule2.setRuleName("affiliate_discount");
        rule2.setCondition("input.userType == 'AFFILIATE' && input.isGrocery == false");
        rule2.setAction("output.setAffiliateDiscountRate(10)");
        rule2.setDescription("affiliate based discount");

        Rule rule3 = new Rule();
        rule3.setRuleName("registration_discount");
        rule3.setCondition("input.isGrocery == false && input.registrationDate.isBefore(now.minusYears(2))" );
        rule3.setAction("output.setRegistrationDiscountRage(5)");
        rule3.setDescription("registration based discount");

        Rule rule4 = new Rule();
        rule4.setRuleName("five_dollar_discount");
        rule4.setCondition("input.totalBillAmount >= 100" );
        rule4.setAction("output.setFiveDollarApplicable(true)");
        rule4.setDescription("five dollar based discount");


        return Lists.newArrayList(rule1, rule2, rule3, rule4);
    }

}
