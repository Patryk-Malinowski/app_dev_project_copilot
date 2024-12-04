
package com.example.project_copilot.service;

import com.example.project_copilot.exceptions.BadDataException;
import com.example.project_copilot.exceptions.NotFoundException;
import com.example.project_copilot.model.Pet;
import com.example.project_copilot.model.PetDTO;
import com.example.project_copilot.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    private Pet testPet;

    @BeforeEach
    void setUp() {
        testPet = new Pet(1L, "Max", "Dog", "Labrador", 3);
    }

    @Test
    void addPet_ValidPet_ReturnsSavedPet() {
        when(petRepository.save(any(Pet.class))).thenReturn(testPet);
        Pet savedPet = petService.addPet(testPet);
        assertNotNull(savedPet);
        assertEquals(testPet.getName(), savedPet.getName());
    }

    @Test
    void addPet_NullPet_ThrowsBadDataException() {
        assertThrows(BadDataException.class, () -> petService.addPet(null));
    }

    @Test
    void getPetById_ExistingId_ReturnsPet() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));
        Pet found = petService.getPetById(1L);
        assertNotNull(found);
        assertEquals(testPet.getId(), found.getId());
    }

    @Test
    void getPetById_NonExistingId_ThrowsNotFoundException() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> petService.getPetById(1L));
    }

    @Test
    void deletePetsByName_ExistingName_DeletesPets() {
        when(petRepository.findByNameIgnoreCase("Max")).thenReturn(List.of(testPet));
        assertDoesNotThrow(() -> petService.deletePetsByName("Max"));
        verify(petRepository).deleteAll(anyList());
    }

    @Test
    void getPetStatistics_ValidData_ReturnsStats() {
        when(petRepository.findAverageAge()).thenReturn(3.5);
        when(petRepository.findOldestAge()).thenReturn(5);

        var stats = petService.getPetStatistics();
        
        assertNotNull(stats);
        assertEquals(3.5, stats.get("averageAge"));
        assertEquals(5, stats.get("oldestAge"));
    }
}