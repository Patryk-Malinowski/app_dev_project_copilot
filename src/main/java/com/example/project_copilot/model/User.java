
package com.example.project_copilot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(unique = true)
    private String username;
    
    private String password;
    private String role;
    private boolean locked;
    private String firstName;
    private String lastName;
    private String county;
}