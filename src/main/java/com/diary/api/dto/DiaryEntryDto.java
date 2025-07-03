package com.diary.api.dto;

import com.diary.api.entity.DiaryEntry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DiaryEntryDto {
    
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    private DiaryEntry.Mood mood;
    
    private DiaryEntry.Weather weather;
    
    private Boolean isPublic = false;
    
    private List<String> tags;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    public DiaryEntryDto() {}
    
    public DiaryEntryDto(String title, String content, LocalDate date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public DiaryEntry.Mood getMood() {
        return mood;
    }
    
    public void setMood(DiaryEntry.Mood mood) {
        this.mood = mood;
    }
    
    public DiaryEntry.Weather getWeather() {
        return weather;
    }
    
    public void setWeather(DiaryEntry.Weather weather) {
        this.weather = weather;
    }
    
    public Boolean getIsPublic() {
        return isPublic;
    }
    
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}