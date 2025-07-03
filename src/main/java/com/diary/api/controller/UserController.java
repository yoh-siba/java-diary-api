package com.diary.api.controller;

import com.diary.api.dto.ApiResponse;
import com.diary.api.dto.UserResponseDto;
import com.diary.api.dto.UserUpdateDto;
import com.diary.api.security.UserPrincipal;
import com.diary.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            UserResponseDto userProfile = userService.getUserProfile(userPrincipal);
            return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully", userProfile));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving user profile: " + e.getMessage()));
        }
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody UserUpdateDto updateDto) {
        
        try {
            UserResponseDto updatedProfile = userService.updateUserProfile(userPrincipal, updateDto);
            return ResponseEntity.ok(ApiResponse.success("User profile updated successfully", updatedProfile));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error updating user profile: " + e.getMessage()));
        }
    }
}