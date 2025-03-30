package com.example.Final_BE.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@RequiredArgsConstructor
@Configuration
public class MqttConfig implements MqttCallback {

    @Value("${mqtt.broker.url}")
    String brokerUrl;

    @Value("${mqtt.client.id}")
    String clientId;

    @Value("${mqtt.username}")
    String username;

    @Value("${mqtt.password}")
    String password;

    @Value("${mqtt.topic}")
    String topic;

    private MqttClient mqttClient;
    private static final String STATUS_TOPIC = "esp/status";
    private static final String DATA_TOPIC = "esp/datasensor";
    private final List<String> messages = new ArrayList<>();

    @PostConstruct
    public void connect() {
        try {
            mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setCleanSession(true);

            log.info("Đang kết nối đến broker: {}", brokerUrl);
            mqttClient.connect(options);
            mqttClient.setCallback(this);
            log.info("Kết nối thành công đến broker: {}", brokerUrl);

            // Subscribe tới topic điều khiển và topic phản hồi
            mqttClient.subscribe(topic);
            mqttClient.subscribe(STATUS_TOPIC);
            mqttClient.subscribe(DATA_TOPIC);

        } catch (MqttException e) {
            log.error("Lỗi khi kết nối đến broker: {}", e.getMessage());
        }
    }

    public void publishMessage(String message) {
        try {
            mqttClient.publish(topic, message.getBytes(), 2, false);
            log.info("Đã gửi tin nhắn '{}' đến chủ đề '{}'", message, topic);
        } catch (MqttException e) {
            log.error("Lỗi khi gửi tin nhắn: {}", e.getMessage());
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("Mất kết nối: {}", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String receivedMessage = new String(message.getPayload());
        log.info("Nhận được tin nhắn từ topic '{}': {}", topic, receivedMessage);

        if (topic.equals(STATUS_TOPIC)) {
            synchronized (messages) {
                messages.add(receivedMessage);
            }
        } else if (topic.equals(DATA_TOPIC)) {
            log.error("Test>>>>>>>>>" + receivedMessage);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    public List<String> getMessages() {
        synchronized (messages) {
            return new ArrayList<>(messages);
        }
    }

    public void clearMessages() {
        synchronized (messages) {
            messages.clear();
        }
    }
}
