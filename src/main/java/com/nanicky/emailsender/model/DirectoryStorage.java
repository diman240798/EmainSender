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
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> emails;
    @Column(name = "body", nullable = false)
    private String body = "";
    @Column(name = "subject", nullable = false)
    private String subject = "";

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return name;
    }
}
