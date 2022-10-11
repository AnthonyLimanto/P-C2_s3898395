package com.example.pc2_personservice.controller;


import com.example.pc2_personservice.dao.PersonDAO;
import com.example.pc2_personservice.exception.PersonNotFoundException;
import com.example.pc2_personservice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {
    @Autowired
    PersonDAO personDAO;
    //  get all notes
    @GetMapping(path = "/persons/person", produces = "application/json")
    public List<Person> getAllNotes() {
        return  personDAO.findAll();
    }
    //  create item
    @PostMapping(path = "/person", consumes = "application/json", produces = "application/json")
    public Person createNote(@RequestBody Person person) {
        return personDAO.save(person);
    }


    //  get one item
    @GetMapping(path = "/person/person{id}", produces = "application/json")
    public Optional<Person> getNoteById(@PathVariable Long id) throws PersonNotFoundException {
        if (!personDAO.findById(id).isPresent()) {
            throw new PersonNotFoundException(id);
        }
        return personDAO.findById(id);
    }
    //    update item
    @PutMapping(path = "/person/person{id}")
    public Person updateNote(@PathVariable Long id, @RequestBody Person personDetails) throws PersonNotFoundException {

        Person person = personDAO.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        person.setName(personDetails.getName());
        person.setAddress(personDetails.getAddress());
        person.setPostcode(personDetails.getPostcode());
        person.setAge(personDetails.getAge());
        person.setEmail(personDetails.getEmail());
        person.setJob(personDetails.getJob());
        person.setPhoneno(personDetails.getPhoneno());

        Person updatedPerson = personDAO.save(person);

        return updatedPerson;
    }

    //    delete item
    @DeleteMapping(path = "/person/person{id}")
    public void deleteBook(@PathVariable Long id) throws PersonNotFoundException {
        Person person = personDAO.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        personDAO.delete(person);

    }

}
