package com.yami.studio.banana.merchantapp.entity.category;

import java.util.List;

public class ListCategory {

    private List<Category> data;

    public ListCategory(List<Category> data) {
        this.data = data;
    }

    public List<Category> getData() {
        return data;
    }
}
