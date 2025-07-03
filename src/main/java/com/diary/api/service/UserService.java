package com.diary.api.service;

import com.diary.api.dto.UserResponseDto;
import com.diary.api.dto.UserUpdateDto;
import com.diary.api.entity.User;
import com.diary.api.repository.UserRepository;
import com.diary.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public UserResponseDto getUserProfile(UserPrincipal userPrincipal) {
        User user = getUserFromPrincipal(userPrincipal);
        return new UserResponseDto(user);
    }
    
    public UserResponseDto updateUserProfile(UserPrincipal userPrincipal, UserUpdateDto updateDto) {
        User user = getUserFromPrincipal(userPrincipal);
        
        if (!user.getEmail().equals(updateDto.getEmail()) && 
            userRepository.existsByEmail(updateDto.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        
        user.setDisplayName(updateDto.getDisplayName());
        user.setEmail(updateDto.getEmail());
        
        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }
    
    private User getUserFromPrincipal(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}