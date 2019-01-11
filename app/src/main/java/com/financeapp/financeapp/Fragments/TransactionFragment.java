package com.financeapp.financeapp.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.financeapp.financeapp.Helpers.DbHelper;
import com.financeapp.financeapp.Models.Transaction;
import com.financeapp.financeapp.R;

import java.util.List;

public class TransactionFragment extends Fragment {

    private View view;
    private Activity activity;
    private DbHelper db;
    private AutoCompleteTextView tagField;
    private AutoCompleteTextView otherPartyField;
    private EditText amountField;
    private RadioGroup transactionTypeField;
    private Button createTransactionButton;
    private Spinner accountSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        activity = getActivity();
        // initializeAccountsSpinner();
        initializeOtherPartyAutocomplete();
        initializeTagAutocomplete();
        initializeCreateTransactionButton();
    }

    private void initializeCreateTransactionButton() {
        tagField = view.findViewById(R.id.tagField);
        amountField = view.findViewById(R.id.amountField);
        transactionTypeField = view.findViewById(R.id.transactionTypeField);

        createTransactionButton = view.findViewById(R.id.createTransactionButton);
        createTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TESTING", "onClick is Called");
                if(tagField.getText().length() > 0 && amountField.getText().length() > 0 && transactionTypeField.getCheckedRadioButtonId() != -1 ) {
                    Log.e("TESTING", "inIF Statement");
                    String tag = tagField.getText().toString();
                    String otherParty = otherPartyField.getText().toString();

                    int amount = (int) (Double.parseDouble(amountField.getText().toString()) * 100);
                    short transactionType = (short) transactionTypeField.getCheckedRadioButtonId();
                    Transaction transaction = new Transaction(tag, otherParty, amount, transactionType);
                    db.makeTransaction(transaction);
                    // Log.e("TESTING", db.getTableAsString("Transactions"));
                    getFragmentManager().popBackStack();
                }
            }
        });
    }

    private void initializeOtherPartyAutocomplete() {
        List<String> otherPartyList = db.getOtherParties();
        otherPartyField = view.findViewById(R.id.otherPartyField);
        otherPartyField.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, otherPartyList));
    }

    private void initializeTagAutocomplete() {
        List<String> tagList = db.getTags();
        tagField = view.findViewById(R.id.tagField);
        tagField.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, tagList));
    }

    // private void initializeAccountsSpinner() {
    //     List<Account> accountList = db.getAllAcccounts();
    //     accountSpinner = findViewById(R.id.accountSpinner);
    //     accountSpinner.setAdapter(new ArrayAdapter<Account>(this, android.R.layout.simple_dropdown_item_1line, accountList));

    // }
}