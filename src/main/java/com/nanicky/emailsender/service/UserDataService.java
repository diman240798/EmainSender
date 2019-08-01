package com.nanicky.emailsender.service;

import com.nanicky.emailsender.crypt.Cryptor;
import com.nanicky.emailsender.dao.UserDataRepo;
import com.nanicky.emailsender.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {
    @Autowired
    private UserDataRepo repo;

    public UserData get() {
        List<UserData> all = repo.findAll();
        if (all.isEmpty())
            return null;
        UserData userData = all.get(0);
        userData.setPassword(Cryptor.crypt(userData.getPassword()));
        return userData;
    }

    public UserData save(String emailFrom, String password) {
        repo.deleteAll();
        UserData userData = new UserData(emailFrom, password);
        userData.setPassword(Cryptor.crypt(userData.getPassword()));
        return repo.save(userData);
    }
}
