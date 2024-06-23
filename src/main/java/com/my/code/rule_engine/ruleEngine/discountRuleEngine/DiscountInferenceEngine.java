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

        BigDecimal maxRate = outputResult.calculateMaxRate();
        BigDecimal netBillAmt = applyDiscount(userInfo.getTotalBillAmount(), maxRate);
        if (outputResult.getFiveDollarApplicable()) {
            BigDecimal fivePercentAmt = getFiveDiscountedAmt(userInfo.getTotalBillAmount());
            netBillAmt = netBillAmt.subtract(fivePercentAmt);
        }
        netBillAmt = netBillAmt.setScale(2, RoundingMode.HALF_UP);
        outputResult.setNetBillAmount(netBillAmt);

    }

    private BigDecimal applyDiscount(BigDecimal amount, BigDecimal discountPercentage) {
        BigDecimal discountDecimal = discountPercentage.divide(new BigDecimal("100"));
        BigDecimal discountAmount = amount.multiply(discountDecimal);
        return amount.subtract(discountAmount);
    }

    public BigDecimal getFiveDiscountedAmt(BigDecimal amount) {
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal fiveDollarDiscountPerHundred = new BigDecimal("5");
        BigDecimal numberOfHundredUnits = amount.divide(hundred, 0, RoundingMode.DOWN);
        return numberOfHundredUnits.multiply(fiveDollarDiscountPerHundred);
    }
}



