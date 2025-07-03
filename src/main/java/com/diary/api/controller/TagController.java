package com.diary.api.controller;

import com.diary.api.dto.ApiResponse;
import com.diary.api.dto.TagDto;
import com.diary.api.security.UserPrincipal;
import com.diary.api.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TagController {
    
    @Autowired
    private TagService tagService;
    
    @GetMapping
    public ResponseEntity<?> getUserTags(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            List<TagDto> tags = tagService.getUserTags(userPrincipal);
            return ResponseEntity.ok(ApiResponse.success("Tags retrieved successfully", tags));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving tags: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createTag(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody TagDto tagDto) {
        
        try {
            TagDto createdTag = tagService.createTag(userPrincipal, tagDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tag created successfully", createdTag));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error creating tag: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        
        try {
            tagService.deleteTag(userPrincipal, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResponse.success("Tag deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error deleting tag: " + e.getMessage()));
        }
    }
}