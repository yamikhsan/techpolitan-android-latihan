package com.yami.studio.banana.merchantapp.entity;

public class Merchant {

    private long merchantId;
    private String merchantName;
    private String merchantSlug;

    public Merchant(long merchantId, String merchantName, String merchantSlug) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.merchantSlug = merchantSlug;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public String getMerchantSlug() {
        return merchantSlug;
    }
}
