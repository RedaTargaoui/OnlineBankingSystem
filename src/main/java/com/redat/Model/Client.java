/**
 * Represents client model
 */
package com.redat.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {
    // Attributes:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientID;
    private String fullName;
    private String password;
}
