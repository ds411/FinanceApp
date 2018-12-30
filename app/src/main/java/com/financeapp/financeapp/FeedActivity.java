package com.financeapp.financeapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class FeedActivity extends AppCompatActivity {

    private FloatingActionButton addTransaction;
    private TextView feedText;

    private SQLiteDatabase db;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        password = intent.getStringExtra("password");
        setContentView(R.layout.activity_feed);

        initializeDb();
        initializeAddTransaction();
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

    private void initializeAddTransaction() {
        feedText = findViewById(R.id.feedText);
        addTransaction = findViewById(R.id.addTransaction);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this, TransactionActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("password", password);
                startActivity(intent);
                reloadFeed();
            }
        });
        reloadFeed();
    }

    private void reloadFeed() {
        feedText.setText("");
        Cursor cursor = db.rawQuery("SELECT amount, time FROM Transactions", null);
        if(cursor.moveToFirst()) {
            do {
                feedText.append(cursor.getString(0) + ", " + cursor.getString(1) + "\n");
            } while(cursor.moveToNext());
        }
    }
}
