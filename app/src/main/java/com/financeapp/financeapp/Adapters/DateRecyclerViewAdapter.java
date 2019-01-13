package com.financeapp.financeapp.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.financeapp.financeapp.Models.Transaction;
import com.financeapp.financeapp.R;

import java.util.Collection;
import java.util.Map;

public class DateRecyclerViewAdapter extends RecyclerView.Adapter<DateRecyclerViewAdapter.ViewHolder> {

    private Map<String, Collection<Transaction>> transactionMap;
    private Object[] keySet;
    private Context mContext;

    public DateRecyclerViewAdapter(Context context, Map<String, Collection<Transaction>> transactionMap) {
        this.transactionMap = transactionMap;
        keySet = transactionMap.keySet().toArray();
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_date, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String date = (String)keySet[i];
        Collection<Transaction> transactionList = transactionMap.get(date);

        viewHolder.dateTag.setText(date);
        viewHolder.transactionListView.setAdapter(new RecyclerAdapter(mContext, transactionList));
        viewHolder.transactionListView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    public int getItemCount() {
        return transactionMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTag;
        RecyclerView transactionListView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTag = itemView.findViewById(R.id.dateTag);
            transactionListView = itemView.findViewById(R.id.transactionListView);
        }
    }
}
