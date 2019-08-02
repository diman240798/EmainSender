package com.nanicky.emailsender.service;

import com.nanicky.emailsender.dao.DirStorRepo;
import com.nanicky.emailsender.model.DirectoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dirStorageService")
public class DirStorageService {
    @Autowired
    DirStorRepo repo;

    List<DirectoryStorage> getAll() {
        return repo.findAll();
    }

    public DirectoryStorage save(DirectoryStorage dir) {
        return repo.save(dir);
    }

    public DirectoryStorage findByPath(String path) {
        return repo.findOneByPath(path);
    }
}
