package com.financeapp.financeapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private EditText tagField;
    private AutoCompleteTextView otherPartyField;
    private EditText amountField;
    private RadioGroup transactionTypeField;
    private Button createTransactionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        initializeDb();
        initializeOtherPartyAutocomplete();
        initializeCreateTransactionButton();
    }

    private void initializeCreateTransactionButton() {
        tagField = findViewById(R.id.tagField);
        amountField = findViewById(R.id.amountField);
        transactionTypeField = findViewById(R.id.transactionTypeField);

        createTransactionButton = findViewById(R.id.createTransactionButton);
        createTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = tagField.getText().toString();
                String otherParty = otherPartyField.getText().toString();
                int amount = (int) Double.parseDouble(amountField.getText().toString()) * 100;
                int transactionType = transactionTypeField.getCheckedRadioButtonId();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                Date now = new Date();
                ContentValues insertValues = new ContentValues();
                insertValues.put("tag", tag);
                insertValues.put("otherParty", otherParty);
                insertValues.put("amount", amount);
                insertValues.put("transactionType", transactionType);
                insertValues.put("date", dateFormat.format(now));
                insertValues.put("time", timeFormat.format(now));
                db.insert("Transactions", "", insertValues);
                finish();
            }
        });
    }

    private void initializeDb() {
        String dbName = "transactions.db";
        File dbDir = getDatabasePath(dbName);
        if(!dbDir.exists()) {
            dbDir.mkdir();
        }
        db = SQLiteDatabase.openOrCreateDatabase(dbDir, null);
    }

    private void initializeOtherPartyAutocomplete() {
        db.execSQL("CREATE TABLE IF NOT EXISTS Transactions(id INTEGER PRIMARY KEY, tag TEXT, otherParty TEXT, amount INTEGER, transactionType INTEGER, date TEXT, time TEXT);");
        Cursor c = db.rawQuery("SELECT DISTINCT otherParty FROM Transactions;", null);
        ArrayList<String> otherPartyList = new ArrayList<>();
        while(c.moveToNext()) {
            otherPartyList.add(c.getString(0));
        }
        otherPartyField = findViewById(R.id.otherPartyField);
        otherPartyField.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, otherPartyList));
    }
}
