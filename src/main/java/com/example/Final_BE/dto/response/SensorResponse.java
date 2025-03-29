package com.example.Final_BE.dto.response;

import com.example.Final_BE.entity.Sensor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorResponse<T>{
    MetaData metaData;
    List<Sensor> data = Collections.emptyList();
}
