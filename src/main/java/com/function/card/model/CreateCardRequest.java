package com.function.card.model;

public class CreateCardRequest {

    private Integer maximumBalance;
    private Integer bankId;
    private Client client;

    public Integer getMaximumBalance() {
        return maximumBalance;
    }
    public void setMaximumBalance(Integer maximumBalance) {
        this.maximumBalance = maximumBalance;
    }
    public Integer getBankId() {
        return bankId;
    }
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    
}
