package com.nanicky.emailsender.service;

import com.nanicky.emailsender.dao.SendingReportRepo;
import com.nanicky.emailsender.model.SendingReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendingReportService {
    @Autowired
    private SendingReportRepo repo;

    public List<SendingReport> getAll() {
        return repo.findAll();
    }

    public void save(List<SendingReport> items) {
        repo.deleteAll();
        repo.saveAll(items);
    }
}
