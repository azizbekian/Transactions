package com.badoo.badootest.data.model;

public class TransactionWrapper {

    public static TransactionWrapper newInstance(String sku, long transactionsCount) {
        return new TransactionWrapper(sku, transactionsCount);
    }

    private String sku;
    private long transactionsCount;

    private TransactionWrapper(String sku, long transactionsCount) {
        this.sku = sku;
        this.transactionsCount = transactionsCount;
    }

    public String getSku() {
        return sku;
    }

    public long getTransactionsCount() {
        return transactionsCount;
    }

}
