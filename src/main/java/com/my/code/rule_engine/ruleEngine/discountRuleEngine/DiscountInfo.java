package com.my.code.rule_engine.ruleEngine.discountRuleEngine;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DiscountInfo {
    private BigDecimal empDiscountRate;
    private BigDecimal affiliateDiscountRate;
    private BigDecimal registrationDiscountRage;
    private boolean isFiveDollarApplicable;
    private BigDecimal netBillAmount;
    private BigDecimal maxDiscountRate;

    public BigDecimal getEmpDiscountRate() {
        return empDiscountRate;
    }

    public void setEmpDiscountRate(BigDecimal empDiscountRate) {
        this.empDiscountRate = empDiscountRate;
    }

    public BigDecimal getAffiliateDiscountRate() {
        return affiliateDiscountRate;
    }

    public void setAffiliateDiscountRate(BigDecimal affiliateDiscountRate) {
        this.affiliateDiscountRate = affiliateDiscountRate;
    }

    public BigDecimal getRegistrationDiscountRage() {
        return registrationDiscountRage;
    }

    public void setRegistrationDiscountRage(BigDecimal registrationDiscountRage) {
        this.registrationDiscountRage = registrationDiscountRage;
    }

    public boolean getFiveDollarApplicable() {
        return isFiveDollarApplicable;
    }

    public void setFiveDollarApplicable(boolean fiveDollarApplicable) {
        this.isFiveDollarApplicable = fiveDollarApplicable;
    }

    public BigDecimal getNetBillAmount() {
        return netBillAmount;
    }

    public void setNetBillAmount(BigDecimal netBillAmount) {
        this.netBillAmount = netBillAmount;
    }

    public BigDecimal calculateMaxRate() {
        List<BigDecimal> discountRates = Arrays.asList(empDiscountRate, affiliateDiscountRate, registrationDiscountRage);
        return discountRates.stream()
                .filter(rate -> rate != null)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

}
