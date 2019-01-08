package com.financeapp.financeapp;

import com.financeapp.financeapp.Fragments.FeedFragment;
import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

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

        initializeApp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initializeApp() {
        passwordInput = findViewById(R.id.passwordInput);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        passwordInputErrors = findViewById(R.id.passwordInputErrors);

        passwordConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordInput.getText().toString();
                String digest = new String(sha256Digest.digest(password.getBytes()));
                if(digest.equals(passwordHash)) {
                    openFeed();
                }
                else {
                    passwordInputErrors.setText("ERROR: Password incorrect.");
                }
            }
        });
    }

    @AfterPermissionGranted(1)
    private void openFeed() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this, permissions)) {
            Intent intent = new Intent(MainActivity.this, FeedFragment.class);
            intent.putExtra("password", password);
            startActivity(intent);
        }
        else {
            EasyPermissions.requestPermissions(this, "This app requires permission to save transaction information.", 1, permissions);
        }
    }
}
