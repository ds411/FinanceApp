package com.financeapp.financeapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.financeapp.financeapp.Adapters.GeneralStatRecyclerAdapter;
import com.financeapp.financeapp.Helpers.DbHelper;
import com.financeapp.financeapp.R;

import java.util.ArrayList;

public class StatFragment extends Fragment {

    private AppCompatActivity activity;
    private View view;

    private RecyclerView feed;

    private DbHelper db;
    private String password;

    private ArrayList<Double> amountStats = new ArrayList<>();
    private ArrayList<String> statName = new ArrayList<>();

    private GeneralStatRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats_feed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        activity = (AppCompatActivity) getActivity();
        dbTransactions();
        initRecyclerView();
    }

    private void initRecyclerView(){
        feed = view.findViewById(R.id.statFeed);
        feed.removeAllViews();

        adapter = new GeneralStatRecyclerAdapter(activity, statName, amountStats);
        feed.setAdapter(adapter);
        feed.setLayoutManager(new LinearLayoutManager(activity));
    }

    private void dbTransactions(){
        amountStats.add(db.getMoneySpentToday());
        amountStats.add(db.getMoneySpentThisWeek());
        amountStats.add(db.getMoneySpentThisMonth());
        amountStats.add(db.getMoneySpentThisYear());
        amountStats.add(db.getMoneySpentAll());

        amountStats.add(db.getMoneyEarnedToday());
        amountStats.add(db.getMoneyEarnedThisWeek());
        amountStats.add(db.getMoneyEarnedThisMonth());
        amountStats.add(db.getMoneyEarnedThisYear());
        amountStats.add(db.getMoneyEarnedAll());

        amountStats.add(db.getNetMoneyToday());
        amountStats.add(db.getNetMoneyThisWeek());
        amountStats.add(db.getNetMoneyThisMonth());
        amountStats.add(db.getNetMoneyThisYear());
        amountStats.add(db.getNetMoneyAll());

    }

}
