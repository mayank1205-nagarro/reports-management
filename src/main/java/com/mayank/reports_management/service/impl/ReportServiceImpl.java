package com.mayank.reports_management.service.impl;

import com.mayank.reports_management.document.Report;
import com.mayank.reports_management.document.ReportHistory;
import com.mayank.reports_management.dto.request.CreateReportRequest;
import com.mayank.reports_management.entity.Department;
import com.mayank.reports_management.repository.DepartmentRepository;
import com.mayank.reports_management.repository.ReportHistoryRepository;
import com.mayank.reports_management.repository.ReportRepository;
import com.mayank.reports_management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final DepartmentRepository departmentRepository;

    private final ReportHistoryRepository historyRepository;

    @Override
    public Report createReport(CreateReportRequest request) {

        Department department =
                departmentRepository.findById(
                                request.getDepartmentId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found"));

        Report report = Report.builder()
                .reportId("REP-" + System.currentTimeMillis())
                .departmentId(department.getId())
                .departmentName(department.getName())
                .description(request.getDescription())
                .status("OPEN")
                .createdDate(LocalDateTime.now())
                .build();

        Report savedReport =
                reportRepository.save(report);

        historyRepository.save(
                ReportHistory.builder()
                        .reportId(savedReport.getReportId())
                        .action("CREATED")
                        .performedBy("admin")
                        .timestamp(LocalDateTime.now())
                        .build());

        return savedReport;
    }

    @Override
    public Report getReportById(String reportId) {

        return reportRepository.findByReportId(reportId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found"));
    }

    @Override
    public List<Report> getAllReports() {

        return reportRepository.findAll();
    }

    @Override
    public List<Report> filterReports(
            String reportId,
            LocalDate fromDate,
            LocalDate toDate) {

        if (reportId != null && !reportId.isBlank()) {

            return reportRepository.findByReportId(reportId)
                    .map(List::of)
                    .orElse(List.of());
        }

        if (fromDate != null && toDate != null) {

            return reportRepository.findByCreatedDateBetween(
                    fromDate.atStartOfDay(),
                    toDate.atTime(23, 59, 59));
        }

        return reportRepository.findAll();
    }
}