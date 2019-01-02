package com.financeapp.financeapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.financeapp.financeapp.Helpers.DbHelper;
import com.financeapp.financeapp.Models.Transaction;

import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private FloatingActionButton addTransaction;
    private TextView feedText;

    private DbHelper db;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        password = intent.getStringExtra("password");
        setContentView(R.layout.activity_feed);

        db = new DbHelper(this);
        initializeAddTransaction();
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
        List<Transaction> transactionList = db.getAllTransactions();
        for(Transaction transaction : transactionList) {
            feedText.append(
                    transaction.getDate() +
                            " " +
                            transaction.getTime() +
                            " $" +
                            transaction.getAmount() +
                            "\n"
            );
        }
    }
}
