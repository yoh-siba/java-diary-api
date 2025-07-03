package com.diary.api.service;

import com.diary.api.dto.StatisticsDto;
import com.diary.api.entity.DiaryEntry;
import com.diary.api.entity.User;
import com.diary.api.repository.DiaryEntryRepository;
import com.diary.api.repository.UserRepository;
import com.diary.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class StatisticsService {
    
    @Autowired
    private DiaryEntryRepository diaryEntryRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public StatisticsDto getUserStatistics(UserPrincipal userPrincipal) {
        User user = getUserFromPrincipal(userPrincipal);
        
        long totalEntries = diaryEntryRepository.countByUser(user);
        
        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();
        long thisMonthEntries = diaryEntryRepository.countByUserAndDateBetween(user, startOfMonth, endOfMonth);
        
        int streak = calculateStreak(user);
        
        Map<String, Integer> moodDistribution = getMoodDistribution(user);
        
        Double avgContentLength = diaryEntryRepository.getAverageContentLengthByUser(user);
        double averageWordsPerEntry = avgContentLength != null ? avgContentLength / 5.0 : 0.0; // Approximate words (assuming 5 chars per word)
        
        return new StatisticsDto(
                (int) totalEntries,
                (int) thisMonthEntries,
                streak,
                moodDistribution,
                Math.round(averageWordsPerEntry * 100.0) / 100.0
        );
    }
    
    private int calculateStreak(User user) {
        List<DiaryEntry> entries = diaryEntryRepository.findByUser(user);
        if (entries.isEmpty()) {
            return 0;
        }
        
        LocalDate today = LocalDate.now();
        LocalDate currentDate = today;
        int streak = 0;
        
        Map<LocalDate, Boolean> entryDates = new HashMap<>();
        for (DiaryEntry entry : entries) {
            entryDates.put(entry.getDate(), true);
        }
        
        while (entryDates.containsKey(currentDate)) {
            streak++;
            currentDate = currentDate.minusDays(1);
        }
        
        if (!entryDates.containsKey(today) && entryDates.containsKey(today.minusDays(1))) {
            currentDate = today.minusDays(1);
            streak = 1;
            
            while (entryDates.containsKey(currentDate)) {
                if (!currentDate.equals(today.minusDays(1))) {
                    streak++;
                }
                currentDate = currentDate.minusDays(1);
            }
        }
        
        return streak;
    }
    
    private Map<String, Integer> getMoodDistribution(User user) {
        List<Object[]> moodCounts = diaryEntryRepository.countByUserGroupByMood(user);
        Map<String, Integer> distribution = new HashMap<>();
        
        for (DiaryEntry.Mood mood : DiaryEntry.Mood.values()) {
            distribution.put(mood.name(), 0);
        }
        
        for (Object[] result : moodCounts) {
            DiaryEntry.Mood mood = (DiaryEntry.Mood) result[0];
            Long count = (Long) result[1];
            if (mood != null) {
                distribution.put(mood.name(), count.intValue());
            }
        }
        
        return distribution;
    }
    
    private User getUserFromPrincipal(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}