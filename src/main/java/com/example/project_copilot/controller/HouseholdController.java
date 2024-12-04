package com.example.project_copilot.controller;

import com.example.project_copilot.model.Household;
import com.example.project_copilot.service.HouseholdService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/households")
@Validated
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @GetMapping("/no-pets")
    public List<Household> getHouseholdsWithNoPets() {
        return householdService.getHouseholdsWithNoPets();
    }

    @GetMapping("/{eircode}")
    public Household getHousehold(@PathVariable String eircode) {
        return householdService.getHouseholdWithPets(eircode);
    }

    @GetMapping("/{eircode}/no-pets")
    public Household getHouseholdWithoutPets(
            @PathVariable @NotBlank(message = "Eircode cannot be empty") String eircode) {
        return householdService.getHouseholdWithoutPets(eircode);
    }

    @GetMapping("/owner-occupied")
    public List<Household> getOwnerOccupiedHouseholds() {
        return householdService.getOwnerOccupiedHouseholds();
    }

    @GetMapping("/statistics")
    public Map<String, Object> getHouseholdStatistics() {
        return householdService.getHouseholdStatistics();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Household createHousehold(@Valid @RequestBody Household household) {
        return householdService.createHousehold(household);
    }

    @PutMapping("/{eircode}")
    public Household updateHousehold(
            @PathVariable @NotBlank(message = "Eircode cannot be empty") String eircode,
            @Valid @RequestBody Household household) {
        return householdService.updateHousehold(eircode, household);
    }

    @DeleteMapping("/{eircode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHousehold(eircode);
    }

    @DeleteMapping("/pets/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePetsByName(
            @PathVariable @NotBlank(message = "Pet name cannot be empty") String name) {
        householdService.deletePetsByName(name);
    }
}