package com.diary.api.controller;

import com.diary.api.dto.ApiResponse;
import com.diary.api.dto.StatisticsDto;
import com.diary.api.security.UserPrincipal;
import com.diary.api.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StatisticsController {
    
    @Autowired
    private StatisticsService statisticsService;
    
    @GetMapping("/summary")
    public ResponseEntity<?> getUserStatistics(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            StatisticsDto statistics = statisticsService.getUserStatistics(userPrincipal);
            return ResponseEntity.ok(ApiResponse.success("Statistics retrieved successfully", statistics));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving statistics: " + e.getMessage()));
        }
    }
}