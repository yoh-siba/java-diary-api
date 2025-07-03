package com.diary.api.repository;

import com.diary.api.entity.DiaryEntry;
import com.diary.api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {
    
    Page<DiaryEntry> findByUserOrderByDateDesc(User user, Pageable pageable);
    
    Page<DiaryEntry> findByUserAndDateOrderByDateDesc(User user, LocalDate date, Pageable pageable);
    
    Page<DiaryEntry> findByUserAndMoodOrderByDateDesc(User user, DiaryEntry.Mood mood, Pageable pageable);
    
    @Query("SELECT de FROM DiaryEntry de JOIN de.diaryTags dt JOIN dt.tag t WHERE de.user = :user AND t.name = :tagName ORDER BY de.date DESC")
    Page<DiaryEntry> findByUserAndTagName(@Param("user") User user, @Param("tagName") String tagName, Pageable pageable);
    
    Optional<DiaryEntry> findByIdAndUser(Long id, User user);
    
    List<DiaryEntry> findByUser(User user);
    
    @Query("SELECT COUNT(de) FROM DiaryEntry de WHERE de.user = :user")
    long countByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(de) FROM DiaryEntry de WHERE de.user = :user AND de.date >= :startDate AND de.date <= :endDate")
    long countByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT de.mood, COUNT(de) FROM DiaryEntry de WHERE de.user = :user GROUP BY de.mood")
    List<Object[]> countByUserGroupByMood(@Param("user") User user);
    
    @Query("SELECT AVG(LENGTH(de.content)) FROM DiaryEntry de WHERE de.user = :user")
    Double getAverageContentLengthByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(DISTINCT de.date) FROM DiaryEntry de WHERE de.user = :user AND de.date <= :endDate ORDER BY de.date DESC")
    long getStreakCount(@Param("user") User user, @Param("endDate") LocalDate endDate);
}