
package com.example.project_copilot.service;

import com.example.project_copilot.model.Household;
import com.example.project_copilot.model.Pet;
import com.example.project_copilot.repository.HouseholdRepository;
import com.example.project_copilot.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import jakarta.persistence.EntityManager;

@Service
public class HouseholdServiceImpl implements HouseholdService {
    
    @Autowired
    private HouseholdRepository householdRepository;
    
    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Household createHousehold(Household household) {
        return householdRepository.save(household);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Household getHouseholdWithPets(String eircode) {
        return householdRepository.findById(eircode)
            .orElseThrow(() -> new RuntimeException("Household not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Household getHouseholdWithoutPets(String eircode) {
        Household household = entityManager.createQuery(
            "SELECT h FROM Household h WHERE h.eircode = :eircode", 
            Household.class)
            .setParameter("eircode", eircode)
            .getSingleResult();
        household.setPets(null);
        return household;
    }

    @Override
    @Transactional
    public Household updateHousehold(String eircode, Household updatedHousehold) {
        Household existing = getHouseholdWithPets(eircode);
        existing.setNumberOfOccupants(updatedHousehold.getNumberOfOccupants());
        existing.setMaxNumberOfOccupants(updatedHousehold.getMaxNumberOfOccupants());
        existing.setOwnerOccupied(updatedHousehold.isOwnerOccupied());
        return householdRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteHousehold(String eircode) {
        Household household = getHouseholdWithPets(eircode);
        if (household.getPets() != null) {
            household.getPets().forEach(pet -> petRepository.delete(pet));
        }
        householdRepository.delete(household);
    }

    @Override
    @Transactional
    public void deletePetsByName(String name) {
        List<Pet> pets = petRepository.findByNameIgnoreCase(name);
        petRepository.deleteAll(pets);
    }

    @Override
    public List<Household> getHouseholdsWithNoPets() {
        return entityManager.createQuery(
            "SELECT h FROM Household h LEFT JOIN h.pets p WHERE p IS NULL", 
            Household.class)
            .getResultList();
    }

    @Override
    public List<Household> getOwnerOccupiedHouseholds() {
        return entityManager.createQuery(
            "SELECT h FROM Household h WHERE h.ownerOccupied = true", 
            Household.class)
            .getResultList();
    }

    @Override
    public Map<String, Object> getHouseholdStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        Long totalHouseholds = householdRepository.count();
        Double avgOccupants = entityManager.createQuery(
            "SELECT AVG(h.numberOfOccupants) FROM Household h", Double.class)
            .getSingleResult();
        Long ownerOccupied = entityManager.createQuery(
            "SELECT COUNT(h) FROM Household h WHERE h.ownerOccupied = true", Long.class)
            .getSingleResult();
        
        stats.put("totalHouseholds", totalHouseholds);
        stats.put("averageOccupants", avgOccupants);
        stats.put("ownerOccupiedCount", ownerOccupied);
        
        return stats;
    }
}