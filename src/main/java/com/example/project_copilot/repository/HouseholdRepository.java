
package com.example.project_copilot.repository;

import com.example.project_copilot.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, String> {
}