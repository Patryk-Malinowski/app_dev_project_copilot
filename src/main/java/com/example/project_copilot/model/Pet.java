package com.example.project_copilot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "animal_type", nullable = false)
    private String animalType;
    
    @Column(nullable = false)
    private String breed;
    
    @Column(nullable = false)
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "household_eircode")
    private Household household;

    public Pet(Long id, String name, String animalType, String breed, Integer age) {
        this.id = id;
        this.name = name;
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
    }

    public Household getHousehold() { return household; }
    public void setHousehold(Household household) { this.household = household; }
}