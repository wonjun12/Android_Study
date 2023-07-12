package com.wonjun.contacts.model;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String phone;

    private int id;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contact(int id, String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }
}
