package com.nanicky.emailsender.service;

import com.nanicky.emailsender.crypt.Cryptor;
import com.nanicky.emailsender.dao.UserDataRepo;
import com.nanicky.emailsender.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {
    @Autowired
    private UserDataRepo repo;

    public UserData get() {
        UserData userData = repo.findAll().get(0);
        userData.setPassword(Cryptor.crypt(userData.getPassword()));
        return userData;
    }

    public UserData save(UserData userData) {
        UserData data = new UserData(userData.getEmail(), userData.getPassword());
        data.setPassword(Cryptor.crypt(data.getPassword()));
        return repo.save(data);
    }
}
