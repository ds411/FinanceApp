package com.financeapp.financeapp.Models;

import android.content.ContentValues;

public class Account {

    private long id;
    private String accountName;
    private double balance;

    public Account() {

    }

    public Account(String accountName, double balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountName", accountName);
        contentValues.put("balance", (int)balance*100);
        return contentValues;
    }

    public long getId() {
        return id;
    }

    public Account setId(long id) {
        this.id = id;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public Account setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public Account setBalance(double balance) {
        this.balance = balance;
        return this;
    }
}
