package com.example.android.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mEmailView,mPasswordView;
    private Button mLoginBtn,mRegBtn;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailView = (EditText) findViewById(R.id.emailTextView);
        mPasswordView = (EditText) findViewById(R.id.passwordTextView);

        mLoginBtn = (Button) findViewById(R.id.login_btn);

        mAuth = FirebaseAuth.getInstance();

    }

    public void signInExistingUser(View view) {
        attemptLogin();
    }

    public void registerNewUser(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(email.equals("") || password.equals("")) { return; }

        Toast.makeText(this,"Login in Progress...",Toast.LENGTH_SHORT).show();

         mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d(TAG,"signInWithEmail() onComplete: "+task.isSuccessful());

                if(!task.isSuccessful()){

                    Log.d(TAG,"Problem signing in: "+ task.getException());
                    showErrorToast("There was a problem signing in");

                }else{
                    Intent intent = new Intent(MainActivity.this,DisplayJournalActivity.class);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }

    private void showErrorToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
