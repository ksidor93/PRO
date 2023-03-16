package com.emontazysta.model.dto;

import com.emontazysta.validation.IsAfter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IsAfter(message = "Date of dismiss must be after date of employment", startDateFieldName = "dateOfEmployment", endDateFieldName = "dateOfDismiss")
public class EmploymentDto {

    private Long id;
    @NotNull(message = "Date of employment cannot be empty")
    private LocalDateTime dateOfEmployment;
    private LocalDateTime dateOfDismiss;
    @NotNull(message = "Company id cannot be empty")
    private Long companyId;
    @NotNull(message = "Employee id cannot be empty")
    private Long employeeId;
}
