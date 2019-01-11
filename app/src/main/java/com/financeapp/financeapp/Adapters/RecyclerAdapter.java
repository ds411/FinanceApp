package com.financeapp.financeapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.financeapp.financeapp.Models.Transaction;
import com.financeapp.financeapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private static final String TAG = "RecycleAdapter";

    private ArrayList<String> amountList = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> tagList = new ArrayList<>();
    private Context mContext;

    public RecyclerAdapter(Context context, List<Transaction> transactionList){
        for(Transaction transaction : transactionList) {
            amountList.add(String.format("$%.2f", transaction.getAmount()));
            dateList.add(transaction.getDate());
            tagList.add(transaction.getTag());
        }
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Log.e(TAG, "onBindViewHolder: called");

        viewHolder.date.setText(dateList.get(i));
        viewHolder.amount.setText(amountList.get(i));


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.e(TAG, "Clicked on thig");
                Toast.makeText(mContext, tagList.get(i), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return amountList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        TextView amount;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

}
