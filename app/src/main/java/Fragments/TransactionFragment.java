package Fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.financeapp.financeapp.Helpers.DbHelper;
import com.financeapp.financeapp.Models.Account;
import com.financeapp.financeapp.Models.Transaction;
import com.financeapp.financeapp.R;

import java.util.List;

public class TransactionFragment extends AppCompatActivity {

    private DbHelper db;
    private EditText tagField;
    private AutoCompleteTextView otherPartyField;
    private EditText amountField;
    private RadioGroup transactionTypeField;
    private Button createTransactionButton;
    private Spinner accountSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_transaction);

        db = new DbHelper(this);

        initializeAccountsSpinner();
        initializeOtherPartyAutocomplete();
        initializeCreateTransactionButton();
    }

    private void initializeCreateTransactionButton() {
        tagField = findViewById(R.id.tagField);
        amountField = findViewById(R.id.amountField);
        transactionTypeField = findViewById(R.id.transactionTypeField);

        createTransactionButton = findViewById(R.id.createTransactionButton);
        createTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tagField.getText().length() > 0 && amountField.getText().length() > 0 && transactionTypeField.getCheckedRadioButtonId() != -1 && otherPartyField.getText().length() > 0 && accountSpinner.getSelectedItem() != null) {
                    String tag = tagField.getText().toString();
                    String otherParty = otherPartyField.getText().toString();

                    int amount = (int) (Double.parseDouble(amountField.getText().toString()) * 100);
                    short transactionType = (short) transactionTypeField.getCheckedRadioButtonId();
                    Transaction transaction = new Transaction(tag, otherParty, amount, transactionType);
                    db.makeTransaction(transaction, ((Account) accountSpinner.getSelectedItem()).getId());

                    finish();
                }
            }
        });
    }

    private void initializeOtherPartyAutocomplete() {
        List<String> otherPartyList = db.getOtherParties();
        otherPartyField = findViewById(R.id.otherPartyField);
        otherPartyField.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, otherPartyList));
    }

    private void initializeAccountsSpinner() {
        List<Account> accountList = db.getAllAcccounts();
        accountSpinner = findViewById(R.id.accountSpinner);
        accountSpinner.setAdapter(new ArrayAdapter<Account>(this, android.R.layout.simple_dropdown_item_1line, accountList));

    }
}
