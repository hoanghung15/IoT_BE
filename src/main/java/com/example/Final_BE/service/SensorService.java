package com.example.Final_BE.service;


import com.example.Final_BE.dto.request.SensorRequest;
import com.example.Final_BE.dto.response.MetaData;
import com.example.Final_BE.dto.response.SensorResponse;
import com.example.Final_BE.entity.Sensor;
import com.example.Final_BE.filter.SensorFilter;
import com.example.Final_BE.repository.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {
    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    ModelMapper modelMapper;

    public SensorResponse<Sensor> getSensores(SensorFilter sensorFilter) {
        int pageNo = sensorFilter.getPageNo();
        int pageSize = sensorFilter.getPageSize();

        String search = (sensorFilter.getSearch() == null || sensorFilter.getSearch().isEmpty()) ? null : sensorFilter.getSearch();
        String sortField = (sensorFilter.getSortField() == null || sensorFilter.getSortField().isEmpty() ? "timestamp" : sensorFilter.getSortField());
        String sortDirection = (sensorFilter.getSortDirection() == null || sensorFilter.getSortDirection().isEmpty() ? "desc" : sensorFilter.getSortDirection());
        String searchField = (sensorFilter.getSearchField() == null || sensorFilter.getSearchField().isEmpty() ? "timestamp" : sensorFilter.getSearchField());
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Sensor> result = sensorRepository.findAllSensorInfor(search, searchField, pageable);

        return SensorResponse.<Sensor>builder()
                .metaData(MetaData.builder()
                        .totalItems(result.getTotalElements())
                        .currentPage(pageNo + 1)
                        .pageSize(result.getSize())
                        .totalPages(result.getTotalPages())
                        .build())
                .data(result.getContent())
                .build();
    }

    public Sensor sensorRealtime() {
        return sensorRepository.findLastSensorInfor();
    }

    public SensorResponse<Sensor> getTop7DataSensor(){
        List<Sensor> sensorList = sensorRepository.findTop7DataSensor();
        return SensorResponse.<Sensor>builder()
                .data(sensorList)
                .build();
    }

    public Sensor putSensorDataRealTime(SensorRequest request){
        Sensor sensor = modelMapper.map(request,Sensor.class);
        return sensorRepository.save(sensor);
    }
}

