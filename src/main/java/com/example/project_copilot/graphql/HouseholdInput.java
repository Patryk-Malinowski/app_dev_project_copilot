
package com.example.project_copilot.graphql;

import lombok.Data;

@Data
public class HouseholdInput {
    private String eircode;
    private int numberOfOccupants;
    private int maxNumberOfOccupants;
    private boolean ownerOccupied;
}