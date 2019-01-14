package com.financeapp.financeapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.financeapp.financeapp.Models.Transaction;
import com.financeapp.financeapp.R;

import java.util.ArrayList;
import java.util.Collection;

public class DateTransactionRecyclerAdapter extends RecyclerView.Adapter<DateTransactionRecyclerAdapter.ViewHolder>{

    private static final String TAG = "RecycleAdapter";

    private ArrayList<Transaction> transactionList;
    private Context mContext;

    public DateTransactionRecyclerAdapter(Context context, Collection<Transaction> transactionList){
        this.transactionList = new ArrayList<>(transactionList);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction_date_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Log.e(TAG, "onBindViewHolder: called");

        final Transaction transaction = transactionList.get(i);

        viewHolder.transactionTag.setText(transaction.getTag());
        viewHolder.transactionOtherParty.setText(transaction.getOtherParty());
        viewHolder.transactionAmount.setText(String.format("$%.2f", transaction.getAmount()));


        /*viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.e(TAG, "Clicked on thig");
                Toast.makeText(mContext, transaction.getTag(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView transactionTag;
        TextView transactionOtherParty;
        TextView transactionAmount;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            transactionTag = itemView.findViewById(R.id.transactionTag);
            transactionOtherParty = itemView.findViewById(R.id.transactionOtherParty);
            transactionAmount = itemView.findViewById(R.id.transactionAmount);
        }
    }

}
