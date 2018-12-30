package com.financeapp.financeapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class TransactionActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        initializeDb();
        db.execSQL("INSERT INTO Transactions(amount, time) VALUES(100, date('now'))");

        finish();
    }

    private void initializeDb() {
        String dbName = "transactions.db";
        File dbDir = getDatabasePath(dbName);
        if(!dbDir.exists()) {
            dbDir.mkdir();
        }
        db = SQLiteDatabase.openOrCreateDatabase(dbDir, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Transactions(id INTEGER PRIMARY KEY, amount INTEGER, time TEXT);");
    }
}
