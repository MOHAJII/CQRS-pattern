package net.haji.projectcqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.haji.projectcqrs.enums.AccountStatus;

@Getter @AllArgsConstructor 
public class AccountActivatedEvent {
    private String accountId;
    private AccountStatus status;
}
