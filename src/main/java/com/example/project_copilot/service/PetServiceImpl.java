package com.example.project_copilot.service;

import com.example.project_copilot.exceptions.BadDataException;
import com.example.project_copilot.exceptions.NotFoundException;
import com.example.project_copilot.model.Pet;
import com.example.project_copilot.model.PetDTO;
import com.example.project_copilot.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet addPet(Pet pet) {
        validatePet(pet);
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found with id: " + id));
    }

    @Override
    public Pet updatePet(Long id, Pet pet) {
        validatePet(pet);
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet not found with id: " + id);
        }
        pet.setId(id);
        return petRepository.save(pet);
    }

    @Override
    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }

    @Override
    public void deletePetsByName(String name) {
        List<Pet> pets = petRepository.findByNameIgnoreCase(name);
        if (pets.isEmpty()) {
            throw new NotFoundException("No pets found with name: " + name);
        }
        petRepository.deleteAll(pets);
    }

    @Override
    public List<Pet> getPetsByAnimalType(String animalType) {
        if (animalType == null || animalType.trim().isEmpty()) {
            throw new BadDataException("Animal type cannot be null or empty");
        }
        return petRepository.findByAnimalTypeIgnoreCase(animalType);
    }

    @Override
    public List<Pet> getPetsByBreedOrderByAge(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            throw new BadDataException("Breed cannot be null or empty");
        }
        return petRepository.findByBreedIgnoreCaseOrderByAgeAsc(breed);
    }

    @Override
    public List<PetDTO> getAllPetsBasicInfo() {
        return petRepository.findAllBasicInfo();
    }

    @Override
    public Map<String, Number> getPetStatistics() {
        Map<String, Number> stats = new HashMap<>();
        Double avgAge = petRepository.findAverageAge();
        Integer oldestAge = petRepository.findOldestAge();
        
        stats.put("averageAge", avgAge != null ? Math.round(avgAge * 10.0) / 10.0 : 0);
        stats.put("oldestAge", oldestAge != null ? oldestAge : 0);
        
        return stats;
    }

    private void validatePet(Pet pet) {
        if (pet == null) {
            throw new BadDataException("Pet cannot be null");
        }
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            throw new BadDataException("Pet name cannot be null or empty");
        }
        if (pet.getAnimalType() == null || pet.getAnimalType().trim().isEmpty()) {
            throw new BadDataException("Pet animal type cannot be null or empty");
        }
        if (pet.getBreed() == null || pet.getBreed().trim().isEmpty()) {
            throw new BadDataException("Pet breed cannot be null or empty");
        }
        if (pet.getAge() == null || pet.getAge() < 0) {
            throw new BadDataException("Pet age must be a non-negative number");
        }
    }
}