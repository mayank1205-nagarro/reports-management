package com.mayank.reports_management.service.impl;

import com.mayank.reports_management.document.Report;
import com.mayank.reports_management.service.ExcelExportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    @Override
    public byte[] exportReportsToExcel(
            List<Report> reports) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            Sheet sheet =
                    workbook.createSheet("Reports");

            String[] headers = {
                    "Report ID",
                    "Department ID",
                    "Department Name",
                    "Description",
                    "Status",
                    "Created Date"
            };

            Row headerRow =
                    sheet.createRow(0);

            for (int column = 0;
                 column < headers.length;
                 column++) {

                Cell cell =
                        headerRow.createCell(column);

                cell.setCellValue(headers[column]);
            }

            int rowNumber = 1;

            for (Report report : reports) {

                Row row =
                        sheet.createRow(rowNumber++);

                row.createCell(0)
                        .setCellValue(
                                valueOrEmpty(report.getReportId()));

                row.createCell(1)
                        .setCellValue(
                                report.getDepartmentId() == null
                                        ? ""
                                        : String.valueOf(
                                                report.getDepartmentId()));

                row.createCell(2)
                        .setCellValue(
                                valueOrEmpty(
                                        report.getDepartmentName()));

                row.createCell(3)
                        .setCellValue(
                                valueOrEmpty(
                                        report.getDescription()));

                row.createCell(4)
                        .setCellValue(
                                valueOrEmpty(
                                        report.getStatus()));

                LocalDateTime createdDate =
                        report.getCreatedDate();

                row.createCell(5)
                        .setCellValue(
                                createdDate == null
                                        ? ""
                                        : createdDate.toString());
            }

            for (int column = 0;
                 column < headers.length;
                 column++) {

                sheet.autoSizeColumn(column);
            }

            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (IOException exception) {

            throw new IllegalStateException(
                    "Unable to create Excel file",
                    exception);
        }
    }

    private String valueOrEmpty(String value) {

        return value == null ? "" : value;
    }
}