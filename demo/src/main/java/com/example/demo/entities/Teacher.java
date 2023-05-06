package com.example.demo.entities;

public class Teacher {
    String name;
    String acTitle;

    public Teacher() {
    }

    public Teacher(String name, String acTitle) {
        this.name = name;
        this.acTitle = acTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcTitle() {
        return acTitle;
    }

    public void setAcTitle(String acTitle) {
        this.acTitle = acTitle;
    }
}
