package com.nanicky.emailsender.service;

import com.nanicky.emailsender.crypt.Cryptor;
import com.nanicky.emailsender.dao.UserDataRepo;
import com.nanicky.emailsender.model.UserData;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDataService {
    @Autowired
    private UserDataRepo repo;

    public UserData get() {
        List<UserData> all = repo.findAll();
        UserData userData = all.isEmpty() ? new UserData("","") : all.get(0);
        userData.setPassword(Cryptor.crypt(userData.getPassword()));
        return userData;
    }

    public UserData save(UserData userData) {
        UserData data = userData.getId() != null ? repo.findById(userData.getId()).get() : new UserData(userData.getEmail(), userData.getPassword());
        data.setPassword(Cryptor.crypt(data.getPassword()));
        return repo.save(data);
    }
}
