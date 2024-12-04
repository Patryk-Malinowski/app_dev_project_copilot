package com.example.project_copilot.controller;

import com.example.project_copilot.model.Household;
import com.example.project_copilot.service.HouseholdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HouseholdController.class)
public class HouseholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseholdService householdService;

    @Autowired
    private ObjectMapper objectMapper;

    private Household testHousehold;

    @BeforeEach
    void setUp() {
        testHousehold = new Household();
        testHousehold.setEircode("D04X4H2");
        testHousehold.setOwnerOccupied(true);
        testHousehold.setNumberOfOccupants(2);        // Add valid number of occupants
        testHousehold.setMaxNumberOfOccupants(4);     // Add valid max occupants
    }

    @Test
    void getAllHouseholds_ShouldReturnHouseholdsList() throws Exception {
        when(householdService.getAllHouseholds()).thenReturn(Arrays.asList(testHousehold));

        mockMvc.perform(get("/api/households"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eircode").value("D04X4H2"));
    }

    @Test
    void createHousehold_ShouldReturnCreatedHousehold() throws Exception {
        when(householdService.createHousehold(any(Household.class))).thenReturn(testHousehold);

        mockMvc.perform(post("/api/households")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testHousehold)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.eircode").value("D04X4H2"));
    }

    @Test
    void getHousehold_WhenExists_ShouldReturnHousehold() throws Exception {
        when(householdService.getHouseholdWithPets("D04X4H2")).thenReturn(testHousehold);

        mockMvc.perform(get("/api/households/D04X4H2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode").value("D04X4H2"));
    }

    @Test
    void updateHousehold_ShouldReturnUpdatedHousehold() throws Exception {
        when(householdService.updateHousehold(eq("D04X4H2"), any(Household.class)))
                .thenReturn(testHousehold);

        mockMvc.perform(put("/api/households/D04X4H2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testHousehold)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode").value("D04X4H2"));
    }

    @Test
    void deleteHousehold_ShouldReturnNoContent() throws Exception {
        doNothing().when(householdService).deleteHousehold("D04X4H2");

        mockMvc.perform(delete("/api/households/D04X4H2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createHousehold_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        Household invalidHousehold = new Household();
        invalidHousehold.setEircode(""); // Invalid: empty eircode
        invalidHousehold.setNumberOfOccupants(0); // Invalid: less than minimum
        invalidHousehold.setMaxNumberOfOccupants(11); // Invalid: exceeds maximum

        mockMvc.perform(post("/api/households")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidHousehold)))
                .andExpect(status().isBadRequest());
    }
}