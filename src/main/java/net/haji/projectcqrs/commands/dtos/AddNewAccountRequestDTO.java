package net.haji.projectcqrs.commands.dtos;

public record AddNewAccountRequestDTO(double initialBalance, String currency) {
}
