package com.financeapp.financeapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.financeapp.financeapp.R;

import java.util.ArrayList;

public class GeneralStatRecyclerAdapter extends RecyclerView.Adapter<GeneralStatRecyclerAdapter.ViewHolder> {

    private ArrayList<Double> amountStats = new ArrayList<>();
    private ArrayList<String> statName = new ArrayList<>();
    private final static String[] statNames = new String[]{
            "Spent Today","Spent Week","Spent Month","Spent Year","Spent Total",
            "Earned Today","Earned Week","Earned Month","Earned Year","Earned Total",
            "Net Today","Net Week","Net Month","Net Year","Net Total"
    };
    private Context mContext;

    public GeneralStatRecyclerAdapter(Context context, ArrayList<String> statName, ArrayList<Double> amountStats) {
        mContext = context;
        this.statName = statName;
        this.amountStats = amountStats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_general_stats, viewGroup, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.statName.setText(statNames[i]);
        viewHolder.statNumber.setText(String.format("$%.2f", amountStats.get(i)));
    }

    @Override
    public int getItemCount() {
        return statNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView statName;
        TextView statNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            statName = itemView.findViewById(R.id.statName);
            statNumber = itemView.findViewById(R.id.statNumber);
        }
    }
}
