package com.example.Final_BE.repository;

import com.example.Final_BE.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    @Query(value = "SELECT * FROM sensor " +
            "WHERE (:searchField = 'timestamp' AND (:search IS NULL OR timestamp LIKE CONCAT('%', :search, '%'))) " +
            "OR (:searchField = 'temperature' AND (:search IS NULL OR temperature = :search)) " +
            "OR (:searchField = 'humidity' AND (:search IS NULL OR humidity = :search)) " +
            "OR (:searchField = 'light' AND (:search IS NULL OR light = :search))",
            nativeQuery = true)
    Page<Sensor> findAllSensorInfor(@Param("search") String search,
                                    @Param("searchField") String searchField,
                                    Pageable pageable);

    @Query(value = "SELECT * FROM sensor ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Sensor findLastSensorInfor();

    @Query(value = "SELECT * FROM sensor ORDER BY timestamp DESC LIMIT 7", nativeQuery = true)
    List<Sensor> findTop7DataSensor();
}
