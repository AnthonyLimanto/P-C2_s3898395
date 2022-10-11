package com.example.pc2_accountservice.exception;

public class AccountNotFoundException extends Exception{
    private Long id;

    public AccountNotFoundException(Long id) {
        super(String.format("Account not found with id : '%s'", id));
    }
}
