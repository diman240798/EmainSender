package com.nanicky.emailsender.service;

import com.nanicky.emailsender.dao.AppDataRepo;
import com.nanicky.emailsender.model.AppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("appDataService")
public class AppDataService {

    @Autowired
    AppDataRepo repo;

    List<AppData> getAll() {
        return repo.findAll();
    }
}
