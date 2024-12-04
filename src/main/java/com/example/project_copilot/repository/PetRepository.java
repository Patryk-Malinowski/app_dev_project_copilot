package com.example.project_copilot.repository;

import com.example.project_copilot.model.Pet;
import com.example.project_copilot.model.PetBasicInfo;
import com.example.project_copilot.model.PetDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNameIgnoreCase(String name);
    List<Pet> findByAnimalTypeIgnoreCase(String animalType);
    List<Pet> findByBreedIgnoreCaseOrderByAgeAsc(String breed);
    
    @Query("SELECT new com.example.project_copilot.model.PetDTO(p.name, p.animalType, p.breed) FROM Pet p")
    List<PetDTO> findAllBasicInfo();
    
    @Query("SELECT AVG(p.age) FROM Pet p")
    Double findAverageAge();
    
    @Query("SELECT MAX(p.age) FROM Pet p")
    Integer findOldestAge();
}