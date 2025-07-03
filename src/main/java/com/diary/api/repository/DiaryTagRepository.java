package com.diary.api.repository;

import com.diary.api.entity.DiaryEntry;
import com.diary.api.entity.DiaryTag;
import com.diary.api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryTagRepository extends JpaRepository<DiaryTag, Long> {
    
    List<DiaryTag> findByDiaryEntry(DiaryEntry diaryEntry);
    
    List<DiaryTag> findByTag(Tag tag);
    
    void deleteByDiaryEntry(DiaryEntry diaryEntry);
    
    void deleteByTag(Tag tag);
}