package com.example.Final_BE.controller;

import com.example.Final_BE.dto.request.SensorRequest;
import com.example.Final_BE.dto.response.ApiResponse;
import com.example.Final_BE.dto.response.SensorResponse;
import com.example.Final_BE.entity.Sensor;
import com.example.Final_BE.filter.SensorFilter;
import com.example.Final_BE.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensor")
@Tag(name = "Sensor - Controller")
public class SensorController {
    @Autowired
    SensorService sensorService;

    @GetMapping("")
    @Operation(summary = "Get All Sensor Infor", description = "Get All Sensor Information")
    ApiResponse<SensorResponse<Sensor>> getAllSensorInf(@Valid @ParameterObject SensorFilter sensorFilter) {
        var result = sensorService.getSensores(sensorFilter);
        return ApiResponse.<SensorResponse<Sensor>>builder()
                .status(HttpStatus.OK.value())
                .message("lấy danh sách cảm bien thành công ")
                .result(result)
                .build();
    }


    @Operation(summary = "Get Sensor Infor lastest",description = "Get sensor information real time")
    @GetMapping("/lastest")
    ApiResponse<Sensor>getSensorInforRealTime(){
        Sensor result = sensorService.sensorRealtime();
        return ApiResponse.<Sensor>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy dữ liệu cảm biến mới nhất thành công")
                .result(result)
                .build();
    }

    @Operation(summary = "Get top7 sensor data", description = "Get top7 sensor data for chart")
    @GetMapping("/top7")
    ApiResponse<SensorResponse<Sensor>> getTop7DataSensor(){
        return  ApiResponse.<SensorResponse<Sensor>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy dữ liệu biểu đồ thành công")
                .result(sensorService.getTop7DataSensor())
                .build();
    }

    @Operation(summary = "Put data sensor realTime", description = "Put data sensor real time")
    @PostMapping("/putData")
    ApiResponse<Sensor> putDataRealTime(@RequestBody  SensorRequest request){
        return ApiResponse.<Sensor>builder()
                .status(HttpStatus.OK.value())
                .message("Update successfully")
                .result(sensorService.putSensorDataRealTime(request))
                .build();
    }
}

