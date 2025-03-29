package com.example.Final_BE.filter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorFilter {
    String search;
    String sortField;
    String sortDirection;
    String searchField;

    @NotNull
    @Min(value = 0)
    Integer pageNo;

    @NotNull
    @Min(value = 1)
    Integer pageSize;
}