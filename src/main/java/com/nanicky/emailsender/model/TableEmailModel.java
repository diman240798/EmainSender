package com.nanicky.emailsender.model;

public class TableEmailModel {
    private String email;

    public TableEmailModel(String email) {
        this.email = email;
    }

    public TableEmailModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
