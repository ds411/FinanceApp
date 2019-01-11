package com.financeapp.financeapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.financeapp.financeapp.MainActivity;
import com.financeapp.financeapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordScreenFragment extends Fragment {

    private static MessageDigest sha256Digest;

    static {
        try{
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
    }

    private View view;

    private EditText passwordInput;
    private Button passwordConfirm;
    private TextView passwordInputErrors;

    //todo:get password hash from db file name
    private String passwordHash = new String(sha256Digest.digest("abc".getBytes()));

    private String password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        initializePasswordInput();
    }

    private void initializePasswordInput() {
        passwordInput = view.findViewById(R.id.passwordInput);
        passwordConfirm = view.findViewById(R.id.passwordConfirm);
        passwordInputErrors = view.findViewById(R.id.passwordInputErrors);

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
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, new FeedFragment(), "feed")
                .addToBackStack(null)
                .commit();
    }
}
