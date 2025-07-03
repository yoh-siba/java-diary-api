package com.diary.api.dto;

import com.diary.api.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagDto {
    
    private Long id;
    
    @NotBlank(message = "Tag name is required")
    @Size(min = 1, max = 50, message = "Tag name must be between 1 and 50 characters")
    private String name;
    
    private Integer usageCount;
    
    public TagDto() {}
    
    public TagDto(String name) {
        this.name = name;
    }
    
    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
    
    public TagDto(Tag tag, Integer usageCount) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.usageCount = usageCount;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getUsageCount() {
        return usageCount;
    }
    
    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }
}