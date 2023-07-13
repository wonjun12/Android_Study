package com.wonjun.training08_memoapp.model;

import java.io.Serializable;

public class Memo implements Serializable {
    private int id;
    private String title;
    private String context;

    public Memo(String title, String context) {
        this.title = title;
        this.context = context;
    }

    public Memo(int id, String title, String context) {
        this.id = id;
        this.title = title;
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
