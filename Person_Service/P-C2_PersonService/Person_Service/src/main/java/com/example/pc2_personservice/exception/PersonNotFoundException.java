package com.example.pc2_personservice.exception;

public class PersonNotFoundException extends Exception{
    private Long id;

    public PersonNotFoundException(Long id) {
        super(String.format("Person not found with id : '%s'", id));
    }
}
