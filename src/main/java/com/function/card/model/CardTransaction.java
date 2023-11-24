package com.function.card.model;

import java.util.Date;

public class CardTransaction {
    private Integer id;
    private String denialReason;
    private Integer status;
    private Integer amount;
    private String commerce;
    private Integer cardId;
    public Integer getCardId() {
        return cardId;
    }
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
    private Date datetime;

    public Date getDatetime() {
        return datetime;
    }
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
