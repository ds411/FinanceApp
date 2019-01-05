package com.financeapp.financeapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.financeapp.financeapp.Models.Transaction;
import com.financeapp.financeapp.R;

import java.util.List;

public class TransactionRecylerViewAdapter extends RecyclerView.Adapter<TransactionRecylerViewAdapter.TransactionViewHolder> {

    List<Transaction> transactionList;

    public TransactionRecylerViewAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_transaction, viewGroup, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder transactionViewHolder, int i) {
        transactionViewHolder.tag.setText(transactionList.get(i).getTag());
        transactionViewHolder.amount.setText(String.format("$%.2f", transactionList.get(i).getAmount()));
        transactionViewHolder.otherParty.setText(transactionList.get(i).getOtherParty());
        transactionViewHolder.accountName.setText(transactionList.get(i).getAccount().getAccountName());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView amount;
        TextView otherParty;
        TextView accountName;
        TextView tag;

        TransactionViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            amount = itemView.findViewById(R.id.amount);
            otherParty = itemView.findViewById(R.id.otherParty);
            accountName = itemView.findViewById(R.id.accountName);
            tag = itemView.findViewById(R.id.tag);
        }
    }
}
