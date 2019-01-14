package com.financeapp.financeapp;

import android.support.v4.app.FragmentManager;
import com.financeapp.financeapp.Fragments.DateFeedFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.financeapp.financeapp.Fragments.PasswordScreenFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private static MessageDigest sha256Digest;

    static {
        try{
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
    }

    private EditText passwordInput;
    private Button passwordConfirm;
    private TextView passwordInputErrors;

    //todo:get password hash from db file name
    private String passwordHash = new String(sha256Digest.digest("abc".getBytes()));

    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new PasswordScreenFragment(), "main")
                .addToBackStack(null)
                .commit();

        //initializeApp();
    }

    private void initializeApp() {
        passwordInput = findViewById(R.id.passwordInput);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        passwordInputErrors = findViewById(R.id.passwordInputErrors);

        passwordConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                password = passwordInput.getText().toString();
//                String digest = new String(sha256Digest.digest(password.getBytes()));
//                if(digest.equals(passwordHash)) {
                    openFeed();
//                }
//                else {
//                    passwordInputErrors.setText("ERROR: Password incorrect.");
//                }
            }
        });
    }

    private void openFeed() {
        Intent intent = new Intent(MainActivity.this, DateFeedFragment.class);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    public FragmentManager getThisSupportedFragmentManager() {
        return getSupportFragmentManager();
    }
}
