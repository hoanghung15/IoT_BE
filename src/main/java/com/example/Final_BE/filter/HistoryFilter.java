package com.example.Final_BE.filter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryFilter {
    String search;
    String deviceName;
    String sortField;
    String sortDirection;

    @NotNull
    @Min(value = 0)
    Integer pageNo;

    @NotNull
    @Min(value = 1)
    Integer pageSize;
}