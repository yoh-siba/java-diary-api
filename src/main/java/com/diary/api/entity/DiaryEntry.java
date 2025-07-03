package com.diary.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "diary_entries")
@EntityListeners(AuditingEntityListener.class)
public class DiaryEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;
    
    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;
    
    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Content is required")
    private String content;
    
    @Column(nullable = false)
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Mood mood;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Weather weather;
    
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = false;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "diaryEntry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiaryTag> diaryTags;
    
    public enum Mood {
        HAPPY, SAD, NORMAL, EXCITED, TIRED
    }
    
    public enum Weather {
        SUNNY, CLOUDY, RAINY, SNOWY
    }
    
    public DiaryEntry() {}
    
    public DiaryEntry(User user, String title, String content, LocalDate date) {
        this.user = user;
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
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
    
    public Mood getMood() {
        return mood;
    }
    
    public void setMood(Mood mood) {
        this.mood = mood;
    }
    
    public Weather getWeather() {
        return weather;
    }
    
    public void setWeather(Weather weather) {
        this.weather = weather;
    }
    
    public Boolean getIsPublic() {
        return isPublic;
    }
    
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
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
    
    public List<DiaryTag> getDiaryTags() {
        return diaryTags;
    }
    
    public void setDiaryTags(List<DiaryTag> diaryTags) {
        this.diaryTags = diaryTags;
    }
}