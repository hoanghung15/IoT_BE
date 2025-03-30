package com.example.Final_BE.service;

import com.example.Final_BE.configuration.MqttConfig;
import com.example.Final_BE.dto.response.SensorResponse;
import com.example.Final_BE.entity.Sensor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorWebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final SensorService sensorService;
    private final MqttConfig mqttConfig;

    @Scheduled(fixedRate = 2000)
    public void sendLatestSensorData(){
        Sensor lastestData = sensorService.sensorRealtime();
        messagingTemplate.convertAndSend("/topic/sensor/latest",lastestData);
    }

    @Scheduled(fixedRate = 2000)
    public void sendTop7SensorData(){
        SensorResponse<Sensor> top7Data = sensorService.getTop7DataSensor();
        messagingTemplate.convertAndSend("/topic/sensor/top7",top7Data);
    }

}
