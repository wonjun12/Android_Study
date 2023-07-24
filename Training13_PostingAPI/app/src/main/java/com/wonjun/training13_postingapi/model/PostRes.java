package com.wonjun.training13_postingapi.model;

import java.util.ArrayList;

public class PostRes {
    String result;
    int count;
    ArrayList<Post> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Post> getItems() {
        return items;
    }

    public void setItems(ArrayList<Post> items) {
        this.items = items;
    }
}
