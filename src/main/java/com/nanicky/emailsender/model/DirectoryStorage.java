package com.nanicky.emailsender.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "DirectoryStorage")
public class DirectoryStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "name")
    private String name;
    @Column(name = "path")
    private String path;
    @Column(name = "emails")
    @ElementCollection
    private List<String> emails;

    public DirectoryStorage(String name, String path, List<String> emails) {
        this.name = name;
        this.path = path;
        this.emails = emails;
    }

    public DirectoryStorage(String name, String path) {
        this.name = name;
        this.path = path;
        emails = new ArrayList<>();
    }

    public DirectoryStorage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public List<String> getEmails() {
        return emails;
    }
}
