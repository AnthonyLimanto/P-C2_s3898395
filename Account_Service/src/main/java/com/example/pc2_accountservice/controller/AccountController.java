package com.example.pc2_accountservice.controller;

import com.example.pc2_accountservice.dao.AccountDAO;
import com.example.pc2_accountservice.exception.AccountNotFoundException;
import com.example.pc2_accountservice.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class AccountController {
    @Autowired
    AccountDAO accountDAO;
    //  get all notes
    @GetMapping(path = "/accounts/account", produces = "application/json")
    public List<Account> getAllNotes() {
        return  accountDAO.findAll();
    }
    //  create item
    @PostMapping(path = "/account", consumes = "application/json", produces = "application/json")
    public Account createNote(@RequestBody Account account) {
        return accountDAO.save(account);
    }


    //  get one item
    @GetMapping(path = "/account/account{id}", produces = "application/json")
    public Optional<Account> getNoteById(@PathVariable Long id) throws AccountNotFoundException {
        if (!accountDAO.findById(id).isPresent()) {
            throw new AccountNotFoundException(id);
        }
        return accountDAO.findById(id);
    }
    //    update item
    @PutMapping(path = "/account/account{id}")
    public Account updateNote(@PathVariable Long id, @RequestBody Account accountDetails) throws AccountNotFoundException {

        Account account = accountDAO.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        account.setAccount_name(accountDetails.getAccount_name());
        account.setBalance(accountDetails.getBalance());
        account.setAcc_num(accountDetails.getAcc_num());
        account.setDate(accountDetails.getDate());
        account.setType(accountDetails.getType());

        Account updatedAccount = accountDAO.save(account);

        return updatedAccount;
    }

    //    delete item
    @DeleteMapping(path = "/account/account{id}")
    public void deleteBook(@PathVariable Long id) throws AccountNotFoundException {
        Account account = accountDAO.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        accountDAO.delete(account);

    }

}
