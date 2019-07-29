package com.nanicky.emailsender.model;

public class TableFileModel {
    public TableFileModel(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public TableFileModel() {

    }

    private String name;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
