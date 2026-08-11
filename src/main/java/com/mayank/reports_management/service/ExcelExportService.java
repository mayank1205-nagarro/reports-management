package com.mayank.reports_management.service;

import com.mayank.reports_management.document.Report;

import java.util.List;

public interface ExcelExportService {

    byte[] exportReportsToExcel(List<Report> reports);
}