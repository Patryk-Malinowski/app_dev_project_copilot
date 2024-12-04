
package com.example.project_copilot.graphql;

import com.example.project_copilot.model.Household;
import com.example.project_copilot.model.Pet;
import com.example.project_copilot.service.HouseholdService;
import com.example.project_copilot.service.PetService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class GraphQLResolver {
    private final HouseholdService householdService;
    private final PetService petService;

    public GraphQLResolver(HouseholdService householdService, PetService petService) {
        this.householdService = householdService;
        this.petService = petService;
    }

    @QueryMapping
    public List<Household> households() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public List<Pet> petsByAnimalType(@Argument String animalType) {
        return petService.getPetsByAnimalType(animalType);
    }

    @QueryMapping
    public Household household(@Argument String eircode) {
        return householdService.getHouseholdWithPets(eircode);
    }

    @QueryMapping
    public Pet pet(@Argument Long id) {
        return petService.getPetById(id);
    }

    @QueryMapping
    public Map<String, Object> householdStatistics() {
        return householdService.getHouseholdStatistics();
    }

    @MutationMapping
    public Household createHousehold(@Argument HouseholdInput input) {
        Household household = new Household();
        household.setEircode(input.getEircode());
        household.setNumberOfOccupants(input.getNumberOfOccupants());
        household.setMaxNumberOfOccupants(input.getMaxNumberOfOccupants());
        household.setOwnerOccupied(input.isOwnerOccupied());
        return householdService.createHousehold(household);
    }

    @MutationMapping
    public boolean deleteHousehold(@Argument String eircode) {
        householdService.deleteHousehold(eircode);
        return true;
    }

    @MutationMapping
    public boolean deletePet(@Argument Long id) {
        petService.deletePetById(id);
        return true;
    }
}