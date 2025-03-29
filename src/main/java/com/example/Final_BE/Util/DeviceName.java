package com.example.Final_BE.Util;

public enum DeviceName {
    DEN("Đèn"),
    QUAT("Quạt");

    private final String label;

    DeviceName(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
