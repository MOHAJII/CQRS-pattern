package net.haji.projectcqrs.commands.dtos;

public record CreditAccountRequestDTO(String accountId, double amount, String currency) {
}
