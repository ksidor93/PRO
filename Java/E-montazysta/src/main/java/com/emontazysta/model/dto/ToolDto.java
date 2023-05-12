package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolDto {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private LocalDate createdAt;
    private String code;
    private List<Long> toolReleases;
    private Long warehouseId;
    private List<Long> toolEvents;
    private Long toolTypeId;
    private boolean deleted;
}
