package com.my.code.rule_engine.ruleEngine.discountRuleEngine;


import java.math.BigDecimal;
import java.time.LocalDate;

public class UserInfo {
    private String userName;
    private String userType;
    private LocalDate registrationDate;
    private boolean isGrocery;
    private BigDecimal totalBillAmount;

    public UserInfo(String userName, UserType userType, LocalDate registrationDate, boolean isGrocery, BigDecimal totalBillAmount) {
        this.userName = userName;
        this.userType = userType.name();
        this.registrationDate = registrationDate;
        this.isGrocery = isGrocery;
        this.totalBillAmount = totalBillAmount;
    }

    public UserInfo(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType.name();
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isGrocery() {
        return isGrocery;
    }

    public void setGrocery(boolean grocery) {
        isGrocery = grocery;
    }

    public BigDecimal getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(BigDecimal totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }
}
