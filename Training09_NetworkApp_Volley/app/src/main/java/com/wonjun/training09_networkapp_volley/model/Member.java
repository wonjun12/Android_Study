package com.wonjun.training09_networkapp_volley.model;

import java.io.Serializable;

public class Member implements Serializable {
    private int id;
    private String name;
    private int salary;
    private int age;

    public Member(String name, int salary, int age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public Member(int id, String name, int salary, int age) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
