package com.mayank.reports_management.controller;

import com.mayank.reports_management.document.Report;
import com.mayank.reports_management.dto.request.CreateReportRequest;
import com.mayank.reports_management.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public Report createReport(
            @Valid @RequestBody CreateReportRequest request) {

        return reportService.createReport(request);
    }

    @GetMapping("/{reportId}")
    public Report getReportById(
            @PathVariable String reportId) {

        return reportService.getReportById(reportId);
    }

    @GetMapping
    public List<Report> getAllReports() {

        return reportService.getAllReports();
    }

    @GetMapping("/search")
    public List<Report> searchReports(
            @RequestParam(required = false) String reportId,

            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate fromDate,

            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate toDate) {

        return reportService.filterReports(
                reportId,
                fromDate,
                toDate);
    }
}