package com.diary.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "diary_tags")
public class DiaryTag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_entry_id", nullable = false)
    @NotNull(message = "Diary entry is required")
    private DiaryEntry diaryEntry;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    @NotNull(message = "Tag is required")
    private Tag tag;
    
    public DiaryTag() {}
    
    public DiaryTag(DiaryEntry diaryEntry, Tag tag) {
        this.diaryEntry = diaryEntry;
        this.tag = tag;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }
    
    public void setDiaryEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
    }
    
    public Tag getTag() {
        return tag;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }
}