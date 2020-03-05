package com.yami.studio.banana.merchantapp.entity.product;

import com.yami.studio.banana.merchantapp.entity.category.Category;
import com.yami.studio.banana.merchantapp.entity.Merchant;

public class Product {

    private long productId;
    private long productQty;
    private long productPrice;
    private long merchantId;
    private long categoryId;

    private String productName;
    private String productSlug;
    private String productImage;
    private String productDesc;

    private Merchant merchant;
    private Category category;

    public long getProductId() {
        return productId;
    }

    public String getProductQty() {
        return String.valueOf(productQty);
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public long getCategoryId() {
        return categoryId;
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

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setProductQty(long productQty) {
        this.productQty = productQty;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductSlug(String productSlug) {
        this.productSlug = productSlug;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
