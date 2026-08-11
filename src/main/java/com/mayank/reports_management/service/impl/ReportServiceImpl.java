package com.mayank.reports_management.service.impl;

import com.mayank.reports_management.document.Report;
import com.mayank.reports_management.document.ReportHistory;
import com.mayank.reports_management.dto.request.AssignReportRequest;
import com.mayank.reports_management.dto.request.CreateReportRequest;
import com.mayank.reports_management.entity.Department;
import com.mayank.reports_management.exception.ResourceNotFoundException;
import com.mayank.reports_management.repository.DepartmentRepository;
import com.mayank.reports_management.repository.ReportHistoryRepository;
import com.mayank.reports_management.repository.ReportRepository;
import com.mayank.reports_management.service.ReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final DepartmentRepository departmentRepository;

    private final ReportHistoryRepository historyRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public Report createReport(CreateReportRequest request, String username) {


        Report report = Report.builder()
                .reportId("REP-" + System.currentTimeMillis())
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
                        .performedBy(username)
                        .timestamp(LocalDateTime.now())
                        .build());

        return savedReport;
    }

@Override
    public Report assignReport(AssignReportRequest request, String username) {

        Department department =
                departmentRepository.findById(
                                request.getDepartmentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found"));

        Report report = reportRepository.findByReportId(
                                request.getReportId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Report not found"));

        report.setDepartmentId(department.getId());
        report.setDepartmentName(department.getName());

        Report savedReport =
                reportRepository.save(report);

        historyRepository.save(
                ReportHistory.builder()
                        .reportId(savedReport.getReportId())
                        .action("ASSIGNED")
                        .performedBy(username)
                        .timestamp(LocalDateTime.now())
                        .build());

        return savedReport;
    }

    @Override
    public Report getReportById(String reportId) {

        return reportRepository.findByReportId(reportId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Report not found"));
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

    @Override
    public List<Report> searchReports(
            String reportId,
            LocalDate fromDate,
            LocalDate toDate) {

        if (fromDate != null
                && toDate == null) {
            throw new IllegalArgumentException(
                    "toDate is required");
        }

        if (fromDate == null
                && toDate != null) {
            throw new IllegalArgumentException(
                    "fromDate is required");
        }

        if (fromDate != null
                && fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException(
                    "fromDate cannot be after toDate");
        }

        List<Criteria> criteriaList = new ArrayList<>();

        if (reportId != null
                && !reportId.isBlank()) {

            criteriaList.add(
                    Criteria.where("reportId")
                            .is(reportId));
        }

        if (fromDate != null) {

            LocalDateTime start =
                    fromDate.atStartOfDay();

            LocalDateTime end =
                    toDate.atTime(
                            23,
                            59,
                            59,
                            999999999);

            criteriaList.add(
                    Criteria.where("createdDate")
                            .gte(start)
                            .lte(end));
        }

        Query query = new Query();

        if (!criteriaList.isEmpty()) {

            query.addCriteria(
                    new Criteria()
                        .andOperator(
                        criteriaList.toArray(
                        new Criteria[0])));
        }

        return mongoTemplate.find(
                query,
                Report.class);
    }

    @Override
    public List<Report> getReportsForExport(
            String status,
            Long departmentId) {

        if (departmentId != null) {
            return reportRepository
                    .findByDepartmentId(departmentId);
        }

        if (status != null && !status.isBlank()) {
            return reportRepository.findByStatus(status);
        }

        return reportRepository.findAll();
    }
}