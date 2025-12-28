package net.haji.projectcqrs.commands.controllers;

import net.haji.projectcqrs.commands.commands.AddAccountCommand;
import net.haji.projectcqrs.commands.commands.CreditAccountCommand;
import net.haji.projectcqrs.commands.commands.DebitAccountCommand;
import net.haji.projectcqrs.commands.dtos.AddNewAccountRequestDTO;
import net.haji.projectcqrs.commands.dtos.CreditAccountRequestDTO;
import net.haji.projectcqrs.commands.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request) {
        return commandGateway.send(new AddAccountCommand(
                java.util.UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request) {
        return commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request) {
        return commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        return e.getMessage();
    }

    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
