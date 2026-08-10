package com.mayank.reports_management.repository;

import com.mayank.reports_management.document.ReportHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportHistoryRepository
        extends MongoRepository<ReportHistory, String> {
}