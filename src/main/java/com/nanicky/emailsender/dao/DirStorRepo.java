package com.nanicky.emailsender.dao;

import com.nanicky.emailsender.model.DirectoryStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirStorRepo extends JpaRepository<DirectoryStorage, String> {

}