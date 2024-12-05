package com.example.project_copilot.controller;

import com.example.project_copilot.model.Pet;
import com.example.project_copilot.model.PetDTO;
import com.example.project_copilot.service.PetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pets")
@Validated
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Pet createPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
    }

    @PatchMapping("/{id}/name")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Pet updatePetName(@PathVariable Long id, @RequestBody Map<String, String> update) {
        Pet pet = petService.getPetById(id);
        pet.setName(update.get("name"));
        return petService.updatePet(id, pet);
    }

    @GetMapping("/type/{animalType}")
    public List<Pet> getPetsByAnimalType(
            @PathVariable @NotBlank(message = "Animal type cannot be empty") String animalType) {
        return petService.getPetsByAnimalType(animalType);
    }

    @GetMapping("/breed/{breed}")
    public List<Pet> getPetsByBreed(
            @PathVariable @NotBlank(message = "Breed cannot be empty") String breed) {
        return petService.getPetsByBreedOrderByAge(breed);
    }

    @GetMapping("/basic-info")
    public List<PetDTO> getAllPetsBasicInfo() {
        return petService.getAllPetsBasicInfo();
    }

    @GetMapping("/statistics")
    public Map<String, Number> getPetStatistics() {
        return petService.getPetStatistics();
    }

    @DeleteMapping("/name/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePetsByName(
            @PathVariable @NotBlank(message = "Name cannot be empty") String name) {
        petService.deletePetsByName(name);
    }
}