package com.example.pc2_accountservice.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Account")
public class Account {
    public Account() {

    }

    @Id
    @GeneratedValue
    private Long id;

//    acc type
    private AccountTypes type;
    private String acc_num;

    private String account_name;

    private double balance;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(String acc_num) {
        this.acc_num = acc_num;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public AccountTypes getType() {
        return type;
    }

    public Account(Long id, AccountTypes type, String acc_num, String account_name, double balance, Date date) {
        this.id = id;
        this.type = type;
        this.acc_num = acc_num;
        this.account_name = account_name;
        this.balance = balance;
        this.date = date;
    }

    public void setType(AccountTypes type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}




