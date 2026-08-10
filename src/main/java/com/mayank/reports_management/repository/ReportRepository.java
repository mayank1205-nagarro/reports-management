package com.mayank.reports_management.repository;

import com.mayank.reports_management.document.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportRepository
        extends MongoRepository<Report, String> {

    Optional<Report> findByReportId(String reportId);

    List<Report> findByCreatedDateBetween(
            LocalDateTime fromDate,
            LocalDateTime toDate);
}