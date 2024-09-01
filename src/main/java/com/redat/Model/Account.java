/**
 * Represents account model
 */
package com.redat.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    // Attributes:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountID;
    private double balance;
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name = "clientID")
    private Client client;
}
