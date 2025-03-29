package com.example.Final_BE.Util;

import jakarta.persistence.AttributeConverter;

public class DeviceNameConverter implements AttributeConverter<DeviceName,String> {

    @Override
    public String convertToDatabaseColumn(DeviceName attribute) {
        return attribute != null ? attribute.getLabel() : null;
    }

    @Override
    public DeviceName convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (DeviceName deviceName : DeviceName.values()) {
            if (deviceName.getLabel().equals(dbData)) {
                return deviceName;
            }
        }
        throw new IllegalArgumentException("Unknown device name: " + dbData);
    }
}
