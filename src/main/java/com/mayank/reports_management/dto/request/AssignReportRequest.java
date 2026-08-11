package com.mayank.reports_management.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for assigning request to a department")
public class AssignReportRequest {

    @NotNull
    @Schema(description = "Department identifier", example = "1")
    private Long departmentId;

    @NotBlank
    @Schema(description = "Report identifier", example = "REP-1786435579958")
    private String reportId;
}