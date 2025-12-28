package net.haji.projectcqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.haji.projectcqrs.enums.AccountStatus;

@Getter @AllArgsConstructor 
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus status;
    private String currency;
}
