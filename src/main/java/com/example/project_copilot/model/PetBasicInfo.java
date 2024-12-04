package com.example.project_copilot.model;

import org.springframework.beans.factory.annotation.Value;

public interface PetBasicInfo {
    @Value("#{target.name}")
    String getName();
    
    @Value("#{target.animalType}")
    String getAnimalType();
    
    @Value("#{target.breed}")
    String getBreed();
}