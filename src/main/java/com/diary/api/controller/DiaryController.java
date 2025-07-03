package com.diary.api.controller;

import com.diary.api.dto.ApiResponse;
import com.diary.api.dto.DiaryEntryDto;
import com.diary.api.entity.DiaryEntry;
import com.diary.api.security.UserPrincipal;
import com.diary.api.service.DiaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/diaries")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DiaryController {
    
    @Autowired
    private DiaryService diaryService;
    
    @GetMapping
    public ResponseEntity<?> getDiaryEntries(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) DiaryEntry.Mood mood,
            @RequestParam(required = false) String tag) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<DiaryEntryDto> diaryEntries = diaryService.getDiaryEntries(userPrincipal, date, mood, tag, pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("content", diaryEntries.getContent());
            response.put("totalElements", diaryEntries.getTotalElements());
            response.put("totalPages", diaryEntries.getTotalPages());
            response.put("currentPage", diaryEntries.getNumber());
            
            return ResponseEntity.ok(ApiResponse.success("Diary entries retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving diary entries: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getDiaryEntry(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        
        try {
            DiaryEntryDto diaryEntry = diaryService.getDiaryEntry(userPrincipal, id);
            return ResponseEntity.ok(ApiResponse.success("Diary entry retrieved successfully", diaryEntry));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving diary entry: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createDiaryEntry(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody DiaryEntryDto diaryEntryDto) {
        
        try {
            DiaryEntryDto createdEntry = diaryService.createDiaryEntry(userPrincipal, diaryEntryDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Diary entry created successfully", createdEntry));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error creating diary entry: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiaryEntry(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id,
            @Valid @RequestBody DiaryEntryDto diaryEntryDto) {
        
        try {
            DiaryEntryDto updatedEntry = diaryService.updateDiaryEntry(userPrincipal, id, diaryEntryDto);
            return ResponseEntity.ok(ApiResponse.success("Diary entry updated successfully", updatedEntry));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error updating diary entry: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiaryEntry(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        
        try {
            diaryService.deleteDiaryEntry(userPrincipal, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResponse.success("Diary entry deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error deleting diary entry: " + e.getMessage()));
        }
    }
}