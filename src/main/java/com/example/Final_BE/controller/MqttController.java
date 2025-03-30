package com.example.Final_BE.controller;

import com.example.Final_BE.configuration.MqttConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttConfig mqttConfig;

    @PostMapping("/publish")
    public String publish(@RequestParam String message) {
        mqttConfig.publishMessage(message);
        return "Đã gửi tin nhắn: " + message;
    }

    @GetMapping("/status")
    public List<String> getStatusMessages() {
        List<String> messages = mqttConfig.getMessages();
        mqttConfig.clearMessages();
        return messages;
    }
}
