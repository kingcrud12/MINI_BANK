package fr.techcrud.minibank.application.port.in;

public record RegisterCustomerCommand(String firstName, String lastName, String email, String password) {}
