package net.haji.projectcqrs.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import net.haji.projectcqrs.commands.commands.AddAccountCommand;
import net.haji.projectcqrs.commands.commands.CreditAccountCommand;
import net.haji.projectcqrs.commands.commands.DebitAccountCommand;
import net.haji.projectcqrs.enums.AccountStatus;
import net.haji.projectcqrs.events.AccountActivatedEvent;
import net.haji.projectcqrs.events.AccountCreatedEvent;
import net.haji.projectcqrs.events.AccountCreditedEvent;
import net.haji.projectcqrs.events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("Handling command: {}", command);
        if (command.getInitialBalance() < 0) throw new IllegalArgumentException("Initial balance cannot be negative");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()));

        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("Handling event: {}", event);
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        log.info("Handling event: {}", event);
        this.accountId = event.getAccountId();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command) {
        log.info("Handling command: {}", command);
        if (status != AccountStatus.CREATED) throw new IllegalStateException("Account is not in CREATED state");
        if (command.getAmount() < 0) throw new IllegalArgumentException("Initial balance cannot be negative");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getAccountId(),
                command.getAmount(),
                command.getCurrency()
        ));

    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        log.info("Handling event: {}", event);
        this.accountId = event.getAccountId();
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        log.info("Handling command: {}", command);
        if (status != AccountStatus.CREATED) throw new IllegalStateException("Account is not in CREATED state");
        if (command.getAmount() < 0) throw new IllegalArgumentException("Initial balance cannot be negative");
        if (command.getAmount() > balance) throw new IllegalArgumentException("Balance is not enough to debit");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getAccountId(),
                command.getAmount(),
                command.getCurrency()
        ));

    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        log.info("Handling event: {}", event);
        this.accountId = event.getAccountId();
        this.balance -= event.getAmount();
    }




}
