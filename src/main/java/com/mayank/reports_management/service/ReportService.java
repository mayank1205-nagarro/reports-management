package com.mayank.reports_management.service;

import com.mayank.reports_management.document.Report;
import com.mayank.reports_management.dto.request.AssignReportRequest;
import com.mayank.reports_management.dto.request.CreateReportRequest;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    Report createReport(CreateReportRequest request, String username);

    Report assignReport(AssignReportRequest request, String username);

    Report getReportById(String reportId);

    List<Report> getReportsForExport(
        String status,
        Long departmentId);

    List<Report> getAllReports();

    List<Report> filterReports(
            String reportId,
            LocalDate fromDate,
            LocalDate toDate);
            
    List<Report> searchReports(
        String reportId,
        LocalDate fromDate,
        LocalDate toDate);
}