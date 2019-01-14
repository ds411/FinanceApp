package com.financeapp.financeapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.financeapp.financeapp.Models.Transaction;
import com.financeapp.financeapp.R;

import java.util.List;

public class FeedTransactionRecyclerViewAdapter extends RecyclerView.Adapter<FeedTransactionRecyclerViewAdapter.ViewHolder> {

    private List<Transaction> transactionList;
    private Context mContext;

    public FeedTransactionRecyclerViewAdapter(Context context, List<Transaction> transactionList) {
        mContext = context;
        this.transactionList = transactionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_transaction, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Transaction transaction = transactionList.get(i);

        viewHolder.otherParty.setText(transaction.getOtherParty());
        viewHolder.amount.setText(String.format("$%.2f", transaction.getAmount()));
        viewHolder.date.setText(transaction.getDate());
        viewHolder.time.setText(transaction.getTime());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView otherParty;
        TextView amount;
        TextView date;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            otherParty = itemView.findViewById(R.id.transactionCardOtherParty);
            amount = itemView.findViewById(R.id.transactionCardAmount);
            date = itemView.findViewById(R.id.transactionCardDate);
            time = itemView.findViewById(R.id.transactionCardTime);
        }
    }
}
