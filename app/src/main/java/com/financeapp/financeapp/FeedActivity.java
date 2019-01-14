package com.financeapp.financeapp;

import android.support.v7.app.AppCompatActivity;

public class FeedActivity extends AppCompatActivity {

//    private FloatingActionButton addTransaction;
//    private TextView feedText;
//
//    private ArrayList<String> amountList = new ArrayList<>();
//    private ArrayList<String> dateList = new ArrayList<>();
//    private ArrayList<String> tagList = new ArrayList<>();
//
//
//    private DbHelper db;
//    String password;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        password = intent.getStringExtra("password");
//
//        setContentView(R.layout.activity_feed);
//
//        db = new DbHelper(this);
//
//        initiateCards();
//        initializeAddTransaction();
//    }
//
//    private void initiateCards(){
//        List<Transaction> transactionList = db.getAllTransactions();
//        for (Transaction transaction : transactionList){
//            amountList.add("$" + transaction.getAmount());
//            dateList.add(transaction.getDate().substring(5, 8) + transaction.getDate().substring(8, 10) + "-" + transaction.getDate().substring(0, 4));
//            tagList.add(transaction.getTag());
//        }
//        initRecyclerView();
//    }
//
//    private void initRecyclerView(){
//        RecyclerView recyclerView = findViewById(R.id.feed);
//        DateTransactionRecyclerAdapter adapter = new DateTransactionRecyclerAdapter(this, dateList, amountList, tagList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//    }
//
//    private void initializeAddTransaction() {
////        feedText = findViewById(R.id.feedText);
//        addTransaction = findViewById(R.id.addTransaction);
//        addTransaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FeedActivity.this, TransactionActivity.class);
//                Bundle bundle = new Bundle();
//                intent.putExtra("password", password);
//                startActivity(intent);
//                finish();
//            }
//        });
//        // reloadFeed();
//    }

    // private void reloadFeed() {
    //     feedText.setText("");
    //     List<Transaction> transactionList = db.getAllTransactions();
    //     for(Transaction transaction : transactionList) {
    //         feedText.append(
    //                 transaction.getDate() +
    //                         " " +
    //                         transaction.getTime() +
    //                         " $" +
    //                         transaction.getAmount() +
    //                         "\n"
    //         );
    //     }
    // }
}

