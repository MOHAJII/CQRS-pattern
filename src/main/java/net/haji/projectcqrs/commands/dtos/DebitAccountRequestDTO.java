package net.haji.projectcqrs.commands.dtos;

public record DebitAccountRequestDTO(String accountId, double amount, String currency) {
}
