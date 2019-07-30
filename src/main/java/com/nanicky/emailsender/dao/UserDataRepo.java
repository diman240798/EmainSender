package com.nanicky.emailsender.dao;

import com.nanicky.emailsender.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepo extends JpaRepository<UserData, String> {
}
