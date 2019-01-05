package com.financeapp.financeapp.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.financeapp.financeapp.Models.Account;
import com.financeapp.financeapp.Models.Transaction;

import java.util.ArrayList;
import java.util.List;

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
                    "date TEXT, " +
                    "time TEXT," +
                    "account_id INTEGER" +
            ");";

    private static final String CREATE_TABLE_ACCOUNTS =
            "CREATE TABLE IF NOT EXISTS Acccunts(" +
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

    public long makeTransaction(Transaction transaction, long accountId) {
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
                    .setDate(c.getString(5))
                    .setTime(c.getString(6))
                    .setAccount(getAccount(c.getLong(7)));
            c.close();
        }

        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM Transactions ORDER BY date DESC;", null);
        while(c.moveToNext()) {
            transactionList.add(
                    new Transaction()
                            .setId(c.getLong(0))
                            .setTag(c.getString(1))
                            .setOtherParty(c.getString(2))
                            .setAmount(c.getInt(3) / 100d)
                            .setTransactionType(c.getShort(4))
                            .setDate(c.getString(5))
                            .setTime(c.getString(6))
                            .setAccount(getAccount(c.getLong(7)))
            );
        }
        c.close();
        return transactionList;
    }

    public List<Transaction> getTransactionsByAccount(Account account) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM Transactions WHERE account_id = " + account.getId() + " ORDER BY date DESC;", null);
        while(c.moveToNext()) {
            transactionList.add(
                    new Transaction()
                            .setId(c.getLong(0))
                            .setTag(c.getString(1))
                            .setOtherParty(c.getString(2))
                            .setAmount(c.getInt(3) / 100d)
                            .setTransactionType(c.getShort(4))
                            .setDate(c.getString(5))
                            .setTime(c.getString(6))
                            .setAccount(getAccount(c.getLong(7)))
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
}
