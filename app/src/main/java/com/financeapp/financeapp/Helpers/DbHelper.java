package com.financeapp.financeapp.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.financeapp.financeapp.Models.Account;
import com.financeapp.financeapp.Models.Transaction;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DbHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "financeDb";
    private final static int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_TRANSACTIONS =
            "CREATE TABLE IF NOT EXISTS Transactions(" +
                    "id INTEGER PRIMARY KEY, " +
                    "tag TEXT, " +
                    "otherParty TEXT, " +
                    "amount INTEGER, " +
                    "transactionType INTEGER, " +
                    "timestamp TEXT DEFAULT CURRENT_TIMESTAMP," +
                    "account_id INTEGER" +
            ");";

    private static final String CREATE_TABLE_ACCOUNTS =
            "CREATE TABLE IF NOT EXISTS Accounts(" +
                    "id INTEGER PRIMARY KEY, " +
                    "accountName TEXT," +
                    "balance INTEGER" +
            ");";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNTS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Transactions;");
        db.execSQL("DROP TABLE IF EXISTS Accounts;");

        onCreate(db);
    }

    public long makeTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = transaction.getContentValues();
        return db.insert("Transactions", null, contentValues);
    }

    public Transaction getTransaction(long transactionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Transaction transaction = null;

        Cursor c = db.rawQuery("SELECT * FROM Transactions WHERE id = " + transactionId, null);
        if(c != null) {
            c.moveToFirst();
            transaction = new Transaction()
                    .setId(c.getLong(0))
                    .setTag(c.getString(1))
                    .setOtherParty(c.getString(2))
                    .setAmount(c.getInt(3) / 100d)
                    .setTransactionType(c.getShort(4))
                    .setDateAndTime(c.getString(5));
                    // .setAccount(getAccount(c.getLong(6)));
            c.close();
        }

        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM Transactions ORDER BY timestamp DESC;", null);
        while(c.moveToNext()) {
            transactionList.add(
                    new Transaction()
                            .setId(c.getLong(0))
                            .setTag(c.getString(1))
                            .setOtherParty(c.getString(2))
                            .setAmount(c.getInt(3) / 100d)
                            .setTransactionType(c.getShort(4))
                            .setDateAndTime(c.getString(5))
                            // .setAccount(getAccount(c.getLong(6)))
            );
        }
        c.close();
        return transactionList;
    }

    public Map<String, Collection<Transaction>> getAllTransactionsGroupedByDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        Multimap<String, Transaction> transactionMultimap = ArrayListMultimap.create();

        Cursor c = db.rawQuery("SELECT * FROM Transactions ORDER BY timestamp DESC;", null);
        while(c.moveToNext()) {
            Transaction transaction = new Transaction()
                            .setId(c.getLong(0))
                            .setTag(c.getString(1))
                            .setOtherParty(c.getString(2))
                            .setAmount(c.getInt(3) / 100d)
                            .setTransactionType(c.getShort(4))
                            .setDateAndTime(c.getString(5));
                    // .setAccount(getAccount(c.getLong(6)));
            transactionMultimap.put(transaction.getDate(), transaction);
        }
        c.close();

        return transactionMultimap.asMap();
    }

    public List<Transaction> getTransactionsByAccount(Account account) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM Transactions WHERE account_id = " + account.getId() + " ORDER BY timestamp DESC;", null);
        while(c.moveToNext()) {
            transactionList.add(
                    new Transaction()
                            .setId(c.getLong(0))
                            .setTag(c.getString(1))
                            .setOtherParty(c.getString(2))
                            .setAmount(c.getInt(3) / 100d)
                            .setTransactionType(c.getShort(4))
                            .setDateAndTime(c.getString(5))
                            // .setAccount(getAccount(c.getLong(6)))
            );
        }
        c.close();
        return transactionList;
    }

    public List<String> getOtherParties() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> otherPartyList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT DISTINCT otherParty FROM Transactions ORDER BY otherParty ASC;", null);
        while(c.moveToNext()) {
            otherPartyList.add(c.getString(0));
        }
        c.close();
        return otherPartyList;
    }

    public List<String> getTags() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> tagList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT DISTINCT tag FROM Transactions ORDER BY tag ASC;", null);
        while(c.moveToNext()) {
            tagList.add(c.getString(0));
        }
        c.close();
        return tagList;
    }

    public long createAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = account.getContentValues();

        return db.insert("Accounts", null, contentValues);
    }

    public Account getAccount(long accountId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Account account = null;

        Cursor c = db.rawQuery("SELECT * FROM Accounts WHERE id = " + accountId, null);

        if(c != null) {
            c.moveToFirst();
            account =  new Account()
                    .setId(c.getLong(0))
                    .setAccountName(c.getString(1))
                    .setBalance(c.getInt(2)/100d);
            c.close();
        }

        return account;
    }

    public List<Account> getAllAcccounts() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Account> accountList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM Accounts ORDER BY accountName ASC;", null);
        while(c.moveToNext()) {
            accountList.add(
                    new Account()
                            .setId(c.getLong(0))
                            .setAccountName(c.getString(1))
                            .setBalance(c.getInt(2)/100d)
            );
        }
        c.close();
        return accountList;
    }

    public double getMoneySpentToday() {
        SQLiteDatabase db = this.getReadableDatabase();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount from Transactions WHERE transactionType = 0 AND timestamp BETWEEN datetime('now', 'start of day') AND datetime('now', 'localtime');", null);
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneySpentThisWeek() {
        SQLiteDatabase db = this.getReadableDatabase();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 0 AND timestamp BETWEEN datetime('now', '-7 days') AND datetime('now', 'localtime');", null);
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneySpentThisMonth() {
        SQLiteDatabase db = this.getReadableDatabase();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 0 AND timestamp BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime');", null);
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneySpentThisYear() {
        SQLiteDatabase db = this.getReadableDatabase();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 0 AND timestamp BETWEEN datetime('now', '-1 year') AND datetime('now', 'localtime');", null);
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneyEarnedToday() {
        SQLiteDatabase db = this.getReadableDatabase();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount from Transactions WHERE transactionType = 1 AND timestamp BETWEEN datetime('now', 'start of day') AND datetime('now', 'localtime');", null);
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneyEarnedThisWeek() {
        SQLiteDatabase db = this.getReadableDatabase();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 1 AND timestamp BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime');", null);
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneyEarnedThisMonth() {
        SQLiteDatabase db = this.getReadableDatabase();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 1 AND timestamp BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime');", null);
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneyEarnedThisYear() {
        SQLiteDatabase db = this.getReadableDatabase();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 1 AND timestamp BETWEEN datetime('now', '-1 year') AND datetime('now', 'localtime');", null);
        while (c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getNetMoneyToday() {
        return getMoneyEarnedToday() - getMoneySpentToday();
    }

    public double getNetMoneyThisWeek() {
        return getMoneyEarnedThisWeek() - getMoneySpentThisWeek();
    }

    public double getNetMoneyThisMonth() {
        return getMoneyEarnedThisMonth() - getMoneySpentThisMonth();
    }

    public double getNetMoneyThisYear() {
        return getMoneyEarnedThisYear() - getMoneySpentThisYear();
    }

}
