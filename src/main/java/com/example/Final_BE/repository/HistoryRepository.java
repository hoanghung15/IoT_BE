package com.example.Final_BE.repository;

import com.example.Final_BE.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    @Query(value = "SELECT * FROM history " +
            "WHERE (:search IS NULL OR timestamp LIKE CONCAT('%', :search, '%')) " +
            "AND (:deviceName IS NULL OR device_name = :deviceName)",
            countQuery = "",
            nativeQuery = true)
    Page<History> findAll(@Param("search") String search, @Param("deviceName") String deviceName, Pageable pageable);

    @Query(value = "SELECT h.* FROM history h " +
            "INNER JOIN ( " +
            "    SELECT device_code, MAX(timestamp) AS latest_timestamp " +
            "    FROM history " +
            "    WHERE device_code IN ('led1', 'led2', 'led3', 'led4') " +
            "    GROUP BY device_code " +
            ") latest ON h.device_code = latest.device_code AND h.timestamp = latest.latest_timestamp " +
            "ORDER BY h.timestamp DESC",
            nativeQuery = true)
    List<History> findLatestLightStatus();

    @Query(value = "select * from history h where h.device_name = 'Quạt' ORDER BY h.timestamp DESC limit 1", nativeQuery = true, countQuery = "")
    History findLatestFanStatus();

}
