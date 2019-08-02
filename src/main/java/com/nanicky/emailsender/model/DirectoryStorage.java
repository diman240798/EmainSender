package com.nanicky.emailsender.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "DirectoryStorage")
@Table(name = "directory_storage")
public class DirectoryStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private String id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_data_id")
    private AppData appData;

    @Column(name = "name")
    private String name;
    @Column(name = "path", unique = true)
    private String path;
    @Column(name = "emails")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> emails;
    @Column(name = "body", nullable = false)
    private String body = "";
    @Column(name = "subject", nullable = false)
    private String subject = "";

    public DirectoryStorage(String name, String path, Set<String> emails) {
        this.name = name;
        this.path = path;
        this.emails = emails;
    }

    public DirectoryStorage(String name, String path) {
        this.name = name;
        this.path = path;
        emails = new HashSet<>();
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

    public Set<String> getEmails() {
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

    public AppData getAppData() {
        return appData;
    }

    public void setAppData(AppData appData) {
        this.appData = appData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectoryStorage that = (DirectoryStorage) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(path, that.path)) return false;
        if (!Objects.equals(emails, that.emails)) return false;
        if (!Objects.equals(body, that.body)) return false;
        return Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }
}
