package com.example.project_copilot.service;

import com.example.project_copilot.model.Pet;
import com.example.project_copilot.model.PetBasicInfo;
import java.util.List;
import java.util.Map;
import com.example.project_copilot.model.PetDTO;

public interface PetService {
    Pet addPet(Pet pet);
    List<Pet> getAllPets();
    Pet getPetById(Long id);
    Pet updatePet(Long id, Pet pet);
    void deletePetById(Long id);
    void deletePetsByName(String name);
    
    List<Pet> getPetsByAnimalType(String animalType);
    List<Pet> getPetsByBreedOrderByAge(String breed);
    List<PetDTO> getAllPetsBasicInfo();
    Map<String, Number> getPetStatistics();
}