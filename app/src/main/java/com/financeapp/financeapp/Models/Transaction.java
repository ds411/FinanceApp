package com.financeapp.financeapp.Models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private long id;
    private String tag;
    private String otherParty;
    private double amount;
    private short transactionType;
    private String date;
    private String time;

    private Date rawDate;

    private Account account;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final String[] transactionTypes = {"Payment", "Receipt"};

    public Transaction() {

    }

    public Transaction(String tag, String otherParty, double amount, short transactionType) {
        this.tag = tag;
        this.otherParty = otherParty;
        this.amount = amount;
        this.transactionType = transactionType;
        this.rawDate = new Date();
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tag", tag);
        contentValues.put("otherParty", otherParty);
        contentValues.put("amount", amount);
        contentValues.put("transactionType", transactionType);
        contentValues.put("date", dateFormat.format(rawDate));
        contentValues.put("time", timeFormat.format(rawDate));
        contentValues.put("account_id", account.getId());
        return contentValues;
    }

    public long getId() {
        return id;
    }

    public Transaction setId(long id) {
        this.id = id;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Transaction setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getOtherParty() {
        return otherParty;
    }

    public Transaction setOtherParty(String otherParty) {
        this.otherParty = otherParty;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Transaction setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public Transaction setTransactionType(short transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public String getTransactionTypeString() {
        return transactionTypes[transactionType];
    }

    public String getDate() {
        return date;
    }

    public Transaction setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Transaction setTime(String time) {
        this.time = time;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public Transaction setAccount(Account account) {
        this.account = account;
        return this;
    }
}
