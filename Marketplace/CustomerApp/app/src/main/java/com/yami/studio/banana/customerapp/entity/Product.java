package com.yami.studio.banana.customerapp.entity;

public class Product {

    private long productId;
    private long productQty;
    private long productPrice;

    private String productName;
    private String productSlug;
    private String productImage;
    private String productDesc;

    private Merchant merchant;
    private Category category;

    public long getProductId() {
        return productId;
    }

    public long getProductQty() {
        return productQty;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductSlug() {
        return productSlug;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Category getCategory() {
        return category;
    }
}
