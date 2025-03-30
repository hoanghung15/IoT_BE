package com.example.Final_BE.service;

import com.example.Final_BE.Util.DeviceName;
import com.example.Final_BE.dto.request.ActivityRequest;
import com.example.Final_BE.dto.response.DeviceStatusListResponse;
import com.example.Final_BE.dto.response.DeviceStatusResponse;
import com.example.Final_BE.dto.response.HistoryResponse;
import com.example.Final_BE.dto.response.MetaData;
import com.example.Final_BE.entity.History;
import com.example.Final_BE.filter.HistoryFilter;
import com.example.Final_BE.repository.HistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    ModelMapper modelMapper;

    public HistoryResponse<History> getHistories(HistoryFilter historyFilter) {
        int pageNo = historyFilter.getPageNo();
        int pageSize = historyFilter.getPageSize();

        String search = (historyFilter.getSearch() == null || historyFilter.getSearch().isEmpty()) ? null : historyFilter.getSearch();
        String deviceName = (historyFilter.getDeviceName() == null || historyFilter.getDeviceName().isEmpty()) ? null : historyFilter.getDeviceName().toUpperCase();
        String sortField = (historyFilter.getSortField() == null || historyFilter.getSortField().isEmpty() ? "timestamp" : historyFilter.getSortField());
        String sortDirection = (historyFilter.getSortDirection() == null || historyFilter.getSortDirection().isEmpty() ? "desc" : historyFilter.getSortDirection());

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<History> result = historyRepository.findAll(search, deviceName, pageable);

        MetaData metaData = MetaData.builder()
                .currentPage(pageNo + 1)
                .pageSize(pageSize)
                .totalItems(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();

        return HistoryResponse.<History>builder()
                .metaData(metaData)
                .data(result.getContent())
                .build();
    }

    public DeviceStatusListResponse getLatestDeviceStatuses() {
        List<DeviceStatusResponse> deviceStatusResponeList = new ArrayList<>();

        List<History> latestLightStatus = historyRepository.findLatestLightStatus();
        History latestFanStatus = historyRepository.findLatestFanStatus();

        for(History h : latestLightStatus){
            DeviceStatusResponse lightResponse = modelMapper.map(h,DeviceStatusResponse.class);
            deviceStatusResponeList.add(lightResponse);
        }

        DeviceStatusResponse fanResponse = modelMapper.map(latestFanStatus, DeviceStatusResponse.class);
        deviceStatusResponeList.add(fanResponse);

        return DeviceStatusListResponse.builder()
                .data(deviceStatusResponeList)
                .build();
    }

    public History putActivity(ActivityRequest activityRequest) {
        History history = new History();
        history.setDeviceCode(activityRequest.getDeviceCode());
        history.setDeviceName(DeviceName.valueOf(activityRequest.getDeviceName()));
        history.setStatus(activityRequest.getStatus());
        return historyRepository.save(history);
    }
}
