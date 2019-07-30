package com.nanicky.emailsender.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "AppData")
public class AppData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @OneToMany(targetEntity=DirectoryStorage.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<DirectoryStorage> dirs;
    @Column(name = "sendingTime")
    private LocalTime sendingTime;

    public AppData(List<DirectoryStorage> dirs, LocalTime sendingTime) {
        this.dirs = dirs;
        this.sendingTime = sendingTime;
    }

    public AppData() {
        dirs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DirectoryStorage> getDirs() {
        return dirs;
    }

    public void setDirs(List<DirectoryStorage> dirs) {
        this.dirs = dirs;
    }

    public LocalTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(LocalTime sendingTime) {
        this.sendingTime = sendingTime;
    }
}
