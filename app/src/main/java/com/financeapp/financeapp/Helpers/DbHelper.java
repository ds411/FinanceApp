package com.financeapp.financeapp.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.financeapp.financeapp.Models.Account;
import com.financeapp.financeapp.Models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "financeDb";
    private final static int DATABASE_VERSION = 1;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    .setDate(c.getString(5))
                    .setTime(c.getString(6));
                    // .setAccount(getAccount(c.getLong(7)));
            c.close();
        }

        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM Transactions ORDER BY date DESC,time DESC;", null);
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
                            // .setAccount(getAccount(c.getLong(7)))
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
                            // .setAccount(getAccount(c.getLong(7)))
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
        Date now = new Date();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount from Transactions WHERE transactionType = 0 AND date = ? ORDER BY time DESC;", new String[]{dateFormat.format(now)});
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneySpentThisWeek() {
        SQLiteDatabase db = this.getReadableDatabase();
        Date now = new Date();
        Date oneWeekAgo = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 0 AND date BETWEEN ? AND ? ORDER BY date DESC, time DESC;", new String[]{dateFormat.format(oneWeekAgo), dateFormat.format(now)});
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneySpentThisMonth() {
        SQLiteDatabase db = this.getReadableDatabase();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -3);
        Date oneMonthAgo = calendar.getTime();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 0 AND date BETWEEN ? AND ? ORDER BY date DESC, time DESC;", new String[]{dateFormat.format(oneMonthAgo), dateFormat.format(now)});
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneySpentThisYear() {
        SQLiteDatabase db = this.getReadableDatabase();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, -1);
        Date oneMonthAgo = calendar.getTime();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 0 AND date BETWEEN ? AND ? ORDER BY date DESC, time DESC;", new String[]{dateFormat.format(oneMonthAgo), dateFormat.format(now)});
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneyEarnedToday() {
        SQLiteDatabase db = this.getReadableDatabase();
        Date now = new Date();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount from Transactions WHERE transactionType = 1 AND date = ? ORDER BY time DESC;", new String[]{dateFormat.format(now)});
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneyEarnedThisWeek() {
        SQLiteDatabase db = this.getReadableDatabase();
        Date now = new Date();
        Date oneWeekAgo = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 1 AND date BETWEEN ? AND ? ORDER BY date DESC, time DESC;", new String[]{dateFormat.format(oneWeekAgo), dateFormat.format(now)});
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneyEarnedThisMonth() {
        SQLiteDatabase db = this.getReadableDatabase();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -3);
        Date oneMonthAgo = calendar.getTime();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 1 AND date BETWEEN ? AND ? ORDER BY date DESC, time DESC;", new String[]{dateFormat.format(oneMonthAgo), dateFormat.format(now)});
        while(c.moveToNext()) {
            spent += c.getInt(0);
        }
        c.close();
        return spent / 100d;
    }

    public double getMoneyEarnedThisYear() {
        SQLiteDatabase db = this.getReadableDatabase();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, -1);
        Date oneMonthAgo = calendar.getTime();
        long spent = 0;

        Cursor c = db.rawQuery("SELECT amount FROM Transactions WHERE transactionType = 1 AND date BETWEEN ? AND ? ORDER BY date DESC, time DESC;", new String[]{dateFormat.format(oneMonthAgo), dateFormat.format(now)});
        while(c.moveToNext()) {
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
