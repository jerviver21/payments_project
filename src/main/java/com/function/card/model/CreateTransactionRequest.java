package com.function.card.model;

public class CreateTransactionRequest {
    private String denialReason;
    private Integer status;
    private String cardNumber;
    private Integer ccv;
    private String expirationDate;
    private Integer amount;
    private String commerce;
    private Integer cardId;
    private String tid;

    public String getTid() {
        return tid;
    }
    public void setTid(String tid) {
        this.tid = tid;
    }
    public Integer getCardId() {
        return cardId;
    }
    public void setCardId(Integer cId) {
        this.cardId = cId;
    }
    
    public String getDenialReason() {
        return denialReason;
    }
    public void setDenialReason(String denialReason) {
        this.denialReason = denialReason;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public Integer getCcv() {
        return ccv;
    }
    public void setCcv(Integer ccv) {
        this.ccv = ccv;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getCommerce() {
        return commerce;
    }
    public void setCommerce(String commerce) {
        this.commerce = commerce;
    }
}
