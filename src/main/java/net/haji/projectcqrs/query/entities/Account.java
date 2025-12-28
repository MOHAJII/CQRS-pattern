package net.haji.projectcqrs.query.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import net.haji.projectcqrs.enums.AccountStatus;

import java.util.Date;

@Entity
public class Account {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
}
