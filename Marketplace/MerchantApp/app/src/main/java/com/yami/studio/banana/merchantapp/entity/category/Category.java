package com.yami.studio.banana.merchantapp.entity.category;

import androidx.annotation.NonNull;

public class Category {

    private long categoryId;
    private String categoryName;

    public long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category(long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @NonNull
    @Override
    public String toString() {
        return categoryName;
    }
}
