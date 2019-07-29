package com.nanicky.emailsender.dao;

import com.nanicky.emailsender.model.AppData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDataRepo extends JpaRepository<AppData, String> {

}

