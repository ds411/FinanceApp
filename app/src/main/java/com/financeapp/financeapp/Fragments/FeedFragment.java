package com.financeapp.financeapp.Fragments;

import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.financeapp.financeapp.Helpers.DbHelper;
import com.financeapp.financeapp.Models.Transaction;
import com.financeapp.financeapp.R;

import java.util.List;

public class FeedFragment extends Fragment {

    private View view;

    private FloatingActionButton addTransaction;
    private RecyclerView feed;

    private DbHelper db;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        initializeAddTransaction();
    }

    private void initializeAddTransaction() {
        feed = view.findViewById(R.id.feed);
        addTransaction = view.findViewById(R.id.addTransaction);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionFragment transactionFragment = new TransactionFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, transactionFragment, "transaction")
                        .addToBackStack(null)
                        .commit();
                //reloadFeed();
            }
        });
        //reloadFeed();
    }

//    private void reloadFeed() {
//        feedText.setText("");
//        List<Transaction> transactionList = db.getAllTransactions();
//        for(Transaction transaction : transactionList) {
//            feedText.append(
//                    transaction.getDate() +
//                            " " +
//                            transaction.getTime() +
//                            " $" +
//                            transaction.getAmount() +
//                            "\n"
//            );
//        }
//        feedText.invalidate();
//    }
}
