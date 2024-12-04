
package com.example.project_copilot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Household {
    @Id
    private String eircode;
    private int numberOfOccupants;
    private int maxNumberOfOccupants;
    private boolean ownerOccupied;

    @OneToMany(mappedBy = "household")
    private List<Pet> pets;

    // Getters and setters
    public String getEircode() { return eircode; }
    public void setEircode(String eircode) { this.eircode = eircode; }
    
    public int getNumberOfOccupants() { return numberOfOccupants; }
    public void setNumberOfOccupants(int numberOfOccupants) { this.numberOfOccupants = numberOfOccupants; }
    
    public int getMaxNumberOfOccupants() { return maxNumberOfOccupants; }
    public void setMaxNumberOfOccupants(int maxNumberOfOccupants) { this.maxNumberOfOccupants = maxNumberOfOccupants; }
    
    public boolean isOwnerOccupied() { return ownerOccupied; }
    public void setOwnerOccupied(boolean ownerOccupied) { this.ownerOccupied = ownerOccupied; }
    
    public List<Pet> getPets() { return pets; }
    public void setPets(List<Pet> pets) { this.pets = pets; }
}