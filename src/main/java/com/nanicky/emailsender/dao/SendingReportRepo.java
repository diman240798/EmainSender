package com.nanicky.emailsender.dao;

import com.nanicky.emailsender.model.SendingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendingReportRepo  extends JpaRepository<SendingReport, String> {
}
