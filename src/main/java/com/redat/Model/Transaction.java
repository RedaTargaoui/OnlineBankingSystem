/**
 * Represents transaction model
 */
package com.redat.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    // Attributes:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionID;
    private TransactionType transactionType;
    private double amount;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "issuerAccount")
    private Account issuerAccount;
    @ManyToOne
    @JoinColumn(name = "recipientAccount")
    private Account recipientAccount;

    public Transaction(TransactionType transactionType, double amount, Date date,
                       Account issuerAccount, Account recipientAccount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
        this.issuerAccount = issuerAccount;
        this.recipientAccount = recipientAccount;
    }
}
