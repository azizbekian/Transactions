package com.badoo.badootest.data.model;

public class ConvertedTransaction extends Transaction {

    public static ConvertedTransaction newInstance(Transaction transaction, float convertedAmount) {
        return new ConvertedTransaction(transaction.getSku(), transaction.amount,
                transaction.currency, convertedAmount);
    }

    private float convertedAmount;

    private ConvertedTransaction(String sku, float amount, String currency, float convertedAmount) {
        super(sku, amount, currency);
        this.convertedAmount = convertedAmount;
    }

    public float getConvertedAmount() {
        return convertedAmount;
    }
}
