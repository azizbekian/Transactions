package com.badoo.badootest.data.model;

public class Transaction {

    protected String sku;
    protected float amount;
    protected String currency;

    public Transaction() {}

    public Transaction(String sku, float amount, String currency) {
        this.sku = sku;
        this.amount = amount;
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
