package com.nanicky.emailsender.service;

import com.nanicky.emailsender.dao.AppDataRepo;
import com.nanicky.emailsender.model.AppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("appDataService")
public class AppDataService {

    @Autowired
    private AppDataRepo repo;

    public AppData get() {
        List<AppData> all = repo.findAll();
        if (all.isEmpty()) return null;
        return all.get(0);
    }

    public AppData save(AppData appData) {
        return repo.save(appData);
    }
}
