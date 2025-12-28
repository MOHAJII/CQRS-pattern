package net.haji.projectcqrs.query.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.haji.projectcqrs.enums.OperationType;

import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;
    private Double amount;
    private Date date;

    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String currency;
}
