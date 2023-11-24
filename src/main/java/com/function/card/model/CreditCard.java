package com.function.card.model;

public class CreditCard {

    private Integer id;
    private String cardNumber; 
    private String expirationDate;
    private Integer ccv;
    private Integer maximumBalance;
    private Integer balance;
    private Integer clientId;
    private Integer bankId;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public Integer getCcv() {
        return ccv;
    }
    public void setCcv(Integer ccv) {
        this.ccv = ccv;
    }
    public Integer getMaximumBalance() {
        return maximumBalance;
    }
    public void setMaximumBalance(Integer maximumBalance) {
        this.maximumBalance = maximumBalance;
    }
    public Integer getBalance() {
        return balance;
    }
    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    public Integer getClientId() {
        return clientId;
    }
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    public Integer getBankId() {
        return bankId;
    }
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }
    
}
