package com.example.Final_BE.controller;

import com.example.Final_BE.dto.request.ActivityRequest;
import com.example.Final_BE.dto.response.ApiResponse;
import com.example.Final_BE.dto.response.HistoryResponse;
import com.example.Final_BE.entity.History;
import com.example.Final_BE.entity.Sensor;
import com.example.Final_BE.filter.HistoryFilter;
import com.example.Final_BE.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
@Tag(name = "History - Controller Device")
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @Operation(summary = "Get all history ", description = "Get all history on / off")
    @GetMapping("")
    ApiResponse<HistoryResponse<History>> getAllHistory(@ParameterObject HistoryFilter historyFilter) {
        var result = historyService.getHistories(historyFilter);
        return ApiResponse.<HistoryResponse<History>>builder()
                .status(HttpStatus.OK.value())
                .message("lấy danh sách lịch sử bật tắt thành công")
                .result(result)
                .build();


    }

    @GetMapping("/statusDevice")
    @Operation(summary = "Get status device", description = "Get status device on/off")
    ApiResponse<Object> getStatusDevice() {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Lấy trạng thái bật tắt mới nhất thành công")
                .result(historyService.getLatestDeviceStatuses())
                .build();
    }

    @Operation(summary = "Activity turn On - Off")
    @PostMapping("/putActivity")
    ApiResponse<Object> putActivity(@ParameterObject ActivityRequest activityRequest) {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật trạng thái thành công")
                .result(historyService.putActivity(activityRequest))
                .build();
    }


}
