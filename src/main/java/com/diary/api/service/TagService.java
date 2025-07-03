package com.diary.api.service;

import com.diary.api.dto.TagDto;
import com.diary.api.entity.Tag;
import com.diary.api.entity.User;
import com.diary.api.repository.TagRepository;
import com.diary.api.repository.UserRepository;
import com.diary.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagService {
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<TagDto> getUserTags(UserPrincipal userPrincipal) {
        User user = getUserFromPrincipal(userPrincipal);
        List<Object[]> tagsWithUsageCount = tagRepository.findByUserWithUsageCount(user);
        
        return tagsWithUsageCount.stream()
                .map(result -> {
                    Tag tag = (Tag) result[0];
                    Long usageCount = (Long) result[1];
                    return new TagDto(tag, usageCount.intValue());
                })
                .collect(Collectors.toList());
    }
    
    public TagDto createTag(UserPrincipal userPrincipal, TagDto tagDto) {
        User user = getUserFromPrincipal(userPrincipal);
        
        if (tagRepository.existsByNameAndUser(tagDto.getName(), user)) {
            throw new RuntimeException("Tag already exists");
        }
        
        Tag tag = new Tag(tagDto.getName(), user);
        Tag savedTag = tagRepository.save(tag);
        
        return new TagDto(savedTag);
    }
    
    public void deleteTag(UserPrincipal userPrincipal, Long id) {
        User user = getUserFromPrincipal(userPrincipal);
        Tag tag = tagRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        
        tagRepository.delete(tag);
    }
    
    private User getUserFromPrincipal(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}