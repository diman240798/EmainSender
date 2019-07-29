package com.nanicky.emailsender.service;

import com.nanicky.emailsender.dao.DirStorRepo;
import com.nanicky.emailsender.model.DirectoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dirStorageService")
public class DirStorageService {
    @Autowired
    DirStorRepo repo;

    List<DirectoryStorage> getAll() {
        return repo.findAll();
    }

}
