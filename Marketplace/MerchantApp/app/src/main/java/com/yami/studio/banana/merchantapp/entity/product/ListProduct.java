package com.yami.studio.banana.merchantapp.entity.product;

import java.util.List;

public class ListProduct {

    private List<Product> data;

    public ListProduct(List<Product> data) {
        this.data = data;
    }

    public List<Product> getData() {
        return data;
    }
}
