package com.example.Final_BE.entity;

import com.example.Final_BE.Util.DeviceName;
import com.example.Final_BE.Util.DeviceNameConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "history")
public class History  extends BaseClass{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "device_code")
    String deviceCode;

    @Column(name = "device_name", nullable = false, columnDefinition = "enum('Đèn','Quạt')")
    @Convert(converter = DeviceNameConverter.class)
    DeviceName deviceName;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    int status;

}
