package com.nanicky.emailsender.model;

import javax.persistence.*;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "SendingReport")
public class SendingReport {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "emailTo")
    private String emailTo;
    @Column(name = "status")
    private Status status;
    @Column(name = "errorText")
    private String errorText;
    @Column(name = "files")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> files;


    public SendingReport() {

    }

    public SendingReport(String emailTo, List<File> files, String errorText) {
        this.emailTo = emailTo;
        this.errorText = errorText;
        status = Status.BAD;
        this.files = files.stream().map(File::getName).collect(Collectors.toList());
    }

    public SendingReport(String emailTo, List<File> files) {
        this.emailTo = emailTo;
        status = Status.OK;
        errorText = "";
        this.files = files.stream().map(File::getName).collect(Collectors.toList());
    }

    public enum Status {
        OK, BAD
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
