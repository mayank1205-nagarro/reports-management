package com.mayank.reports_management.controller;

import com.mayank.reports_management.document.Report;
import com.mayank.reports_management.dto.request.AssignReportRequest;
import com.mayank.reports_management.dto.request.CreateReportRequest;
import com.mayank.reports_management.service.ExcelExportService;
import com.mayank.reports_management.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(
    name = "Reports Management",
    description = "APIs for creating, assigning and viewing reports"
)
public class ReportController {

    private final ReportService reportService;
    private final ExcelExportService excelExportService;

    @PostMapping
    public Report createReport(
            @Valid @RequestBody CreateReportRequest request, Authentication authentication) {
        String username = authentication.getName();

        return reportService.createReport(request, username);
    }

    @PostMapping("/assign")
    public Report assignReport(
            @Valid @RequestBody AssignReportRequest request, Authentication authentication) {
        String username = authentication.getName();

        return reportService.assignReport(request, username);
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

    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Export reports to Excel",
            description = "Downloads multiple reports as an XLSX file. "
                    + "Optional filters can be applied by status or department."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Excel file generated successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication token missing or invalid"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User does not have permission"
            )
    })
    public ResponseEntity<byte[]> exportReports(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long departmentId) {

        List<Report> reports =
                reportService.getReportsForExport(
                        status,
                        departmentId);

        byte[] file =
                excelExportService.exportReportsToExcel(
                        reports);

        String filename =
                "reports-"
                        + LocalDateTime.now()
                        .toLocalDate()
                        + ".xlsx";

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
}