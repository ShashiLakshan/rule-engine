package com.my.code.rule_engine.ruleEngine.discountRuleEngine;

import com.my.code.rule_engine.parser.RuleParser;
import com.my.code.rule_engine.ruleEngine.InferenceEngine;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;

import java.math.BigDecimal;

@Service
public class DiscountInferenceEngine extends InferenceEngine<UserInfo, DiscountInfo> {

    protected DiscountInferenceEngine(RuleParser<UserInfo, DiscountInfo> ruleParser) {
        super(ruleParser);
    }

    @Override
    protected DiscountInfo initializeOutputResult() {
        return new DiscountInfo();
    }

    @Override
    protected void calculateNetBillAmt(UserInfo userInfo, DiscountInfo outputResult) {

        BigDecimal maxRate = outputResult.getMaxDiscountRate();
        BigDecimal netBillAmt = applyDiscount(userInfo.getTotalBillAmount(), maxRate);
        outputResult.setNetBillAmount(netBillAmt);

    }

    private BigDecimal applyDiscount(BigDecimal amount, BigDecimal discountPercentage) {
        BigDecimal discountDecimal = discountPercentage.divide(new BigDecimal("100"));
        BigDecimal discountAmount = amount.multiply(discountDecimal);
        BigDecimal netAmt = amount.subtract(discountAmount);
        netAmt = netAmt.setScale(2, RoundingMode.HALF_UP);
        return netAmt;
    }
}



