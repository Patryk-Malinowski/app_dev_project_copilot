
package com.example.project_copilot.service;

import com.example.project_copilot.model.Household;
import java.util.List;
import java.util.Map;

public interface HouseholdService {
    Household createHousehold(Household household);
    List<Household> getAllHouseholds();
    Household getHouseholdWithPets(String eircode);
    Household getHouseholdWithoutPets(String eircode);
    Household updateHousehold(String eircode, Household household);
    void deleteHousehold(String eircode);
    void deletePetsByName(String name);
    List<Household> getHouseholdsWithNoPets();
    List<Household> getOwnerOccupiedHouseholds();
    Map<String, Object> getHouseholdStatistics();
}