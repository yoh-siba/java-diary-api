package com.diary.api.repository;

import com.diary.api.entity.Tag;
import com.diary.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    List<Tag> findByUserOrderByNameAsc(User user);
    
    Optional<Tag> findByIdAndUser(Long id, User user);
    
    Optional<Tag> findByNameAndUser(String name, User user);
    
    boolean existsByNameAndUser(String name, User user);
    
    @Query("SELECT t, COUNT(dt) as usageCount FROM Tag t LEFT JOIN t.diaryTags dt WHERE t.user = :user GROUP BY t ORDER BY usageCount DESC")
    List<Object[]> findByUserWithUsageCount(@Param("user") User user);
}