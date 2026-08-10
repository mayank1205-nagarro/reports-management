package com.mayank.reports_management.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDocument {

    @Id
    private String id;

    private String reportId;

    private Long departmentId;

    private String departmentName;

    private String description;

    private String status;

    private LocalDateTime createdDate;
}