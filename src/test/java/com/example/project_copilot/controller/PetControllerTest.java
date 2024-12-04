
package com.example.project_copilot.controller;

import com.example.project_copilot.model.Pet;
import com.example.project_copilot.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pet testPet;

    @BeforeEach
    void setUp() {
        testPet = new Pet();
        testPet.setId(1L);
        testPet.setName("TestPet");
        testPet.setAnimalType("Dog");
        testPet.setBreed("Labrador");
        testPet.setAge(3);
    }

    @Test
    void getAllPets_ShouldReturnPetsList() throws Exception {
        when(petService.getAllPets()).thenReturn(Arrays.asList(testPet));

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("TestPet"))
                .andExpect(jsonPath("$[0].animalType").value("Dog"));
    }

    @Test
    void createPet_ShouldReturnCreatedPet() throws Exception {
        when(petService.addPet(any(Pet.class))).thenReturn(testPet);

        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPet)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("TestPet"));
    }

    @Test
    void getPetById_WhenPetExists_ShouldReturnPet() throws Exception {
        when(petService.getPetById(1L)).thenReturn(testPet);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestPet"));
    }

    @Test
    void deletePet_ShouldReturnNoContent() throws Exception {
        doNothing().when(petService).deletePetById(1L);

        mockMvc.perform(delete("/api/pets/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updatePetName_ShouldReturnUpdatedPet() throws Exception {
        Pet updatedPet = new Pet();
        updatedPet.setId(1L);
        updatedPet.setName("UpdatedName");
        
        when(petService.getPetById(1L)).thenReturn(testPet);
        when(petService.updatePet(eq(1L), any(Pet.class))).thenReturn(updatedPet);

        mockMvc.perform(patch("/api/pets/1/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"UpdatedName\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedName"));
    }
}