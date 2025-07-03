package com.diary.api.service;

import com.diary.api.dto.DiaryEntryDto;
import com.diary.api.entity.DiaryEntry;
import com.diary.api.entity.DiaryTag;
import com.diary.api.entity.Tag;
import com.diary.api.entity.User;
import com.diary.api.repository.DiaryEntryRepository;
import com.diary.api.repository.DiaryTagRepository;
import com.diary.api.repository.TagRepository;
import com.diary.api.repository.UserRepository;
import com.diary.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiaryService {
    
    @Autowired
    private DiaryEntryRepository diaryEntryRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private DiaryTagRepository diaryTagRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Page<DiaryEntryDto> getDiaryEntries(UserPrincipal userPrincipal, LocalDate date, 
                                             DiaryEntry.Mood mood, String tagName, Pageable pageable) {
        User user = getUserFromPrincipal(userPrincipal);
        Page<DiaryEntry> diaryEntries;
        
        if (date != null) {
            diaryEntries = diaryEntryRepository.findByUserAndDateOrderByDateDesc(user, date, pageable);
        } else if (mood != null) {
            diaryEntries = diaryEntryRepository.findByUserAndMoodOrderByDateDesc(user, mood, pageable);
        } else if (tagName != null) {
            diaryEntries = diaryEntryRepository.findByUserAndTagName(user, tagName, pageable);
        } else {
            diaryEntries = diaryEntryRepository.findByUserOrderByDateDesc(user, pageable);
        }
        
        List<DiaryEntryDto> dtos = diaryEntries.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return new PageImpl<>(dtos, pageable, diaryEntries.getTotalElements());
    }
    
    public DiaryEntryDto getDiaryEntry(UserPrincipal userPrincipal, Long id) {
        User user = getUserFromPrincipal(userPrincipal);
        DiaryEntry diaryEntry = diaryEntryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Diary entry not found"));
        
        return convertToDto(diaryEntry);
    }
    
    public DiaryEntryDto createDiaryEntry(UserPrincipal userPrincipal, DiaryEntryDto diaryEntryDto) {
        User user = getUserFromPrincipal(userPrincipal);
        
        DiaryEntry diaryEntry = new DiaryEntry();
        diaryEntry.setUser(user);
        diaryEntry.setTitle(diaryEntryDto.getTitle());
        diaryEntry.setContent(diaryEntryDto.getContent());
        diaryEntry.setDate(diaryEntryDto.getDate());
        diaryEntry.setMood(diaryEntryDto.getMood());
        diaryEntry.setWeather(diaryEntryDto.getWeather());
        diaryEntry.setIsPublic(diaryEntryDto.getIsPublic() != null ? diaryEntryDto.getIsPublic() : false);
        
        DiaryEntry savedEntry = diaryEntryRepository.save(diaryEntry);
        
        if (diaryEntryDto.getTags() != null && !diaryEntryDto.getTags().isEmpty()) {
            saveTags(savedEntry, diaryEntryDto.getTags(), user);
        }
        
        return convertToDto(savedEntry);
    }
    
    public DiaryEntryDto updateDiaryEntry(UserPrincipal userPrincipal, Long id, DiaryEntryDto diaryEntryDto) {
        User user = getUserFromPrincipal(userPrincipal);
        DiaryEntry diaryEntry = diaryEntryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Diary entry not found"));
        
        diaryEntry.setTitle(diaryEntryDto.getTitle());
        diaryEntry.setContent(diaryEntryDto.getContent());
        diaryEntry.setDate(diaryEntryDto.getDate());
        diaryEntry.setMood(diaryEntryDto.getMood());
        diaryEntry.setWeather(diaryEntryDto.getWeather());
        diaryEntry.setIsPublic(diaryEntryDto.getIsPublic() != null ? diaryEntryDto.getIsPublic() : false);
        
        diaryTagRepository.deleteByDiaryEntry(diaryEntry);
        
        DiaryEntry updatedEntry = diaryEntryRepository.save(diaryEntry);
        
        if (diaryEntryDto.getTags() != null && !diaryEntryDto.getTags().isEmpty()) {
            saveTags(updatedEntry, diaryEntryDto.getTags(), user);
        }
        
        return convertToDto(updatedEntry);
    }
    
    public void deleteDiaryEntry(UserPrincipal userPrincipal, Long id) {
        User user = getUserFromPrincipal(userPrincipal);
        DiaryEntry diaryEntry = diaryEntryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Diary entry not found"));
        
        diaryEntryRepository.delete(diaryEntry);
    }
    
    private User getUserFromPrincipal(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    private void saveTags(DiaryEntry diaryEntry, List<String> tagNames, User user) {
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByNameAndUser(tagName, user)
                    .orElseGet(() -> {
                        Tag newTag = new Tag(tagName, user);
                        return tagRepository.save(newTag);
                    });
            
            DiaryTag diaryTag = new DiaryTag(diaryEntry, tag);
            diaryTagRepository.save(diaryTag);
        }
    }
    
    private DiaryEntryDto convertToDto(DiaryEntry diaryEntry) {
        DiaryEntryDto dto = new DiaryEntryDto();
        dto.setId(diaryEntry.getId());
        dto.setTitle(diaryEntry.getTitle());
        dto.setContent(diaryEntry.getContent());
        dto.setDate(diaryEntry.getDate());
        dto.setMood(diaryEntry.getMood());
        dto.setWeather(diaryEntry.getWeather());
        dto.setIsPublic(diaryEntry.getIsPublic());
        dto.setCreatedAt(diaryEntry.getCreatedAt());
        dto.setUpdatedAt(diaryEntry.getUpdatedAt());
        
        List<String> tagNames = diaryEntry.getDiaryTags().stream()
                .map(diaryTag -> diaryTag.getTag().getName())
                .collect(Collectors.toList());
        dto.setTags(tagNames);
        
        return dto;
    }
}