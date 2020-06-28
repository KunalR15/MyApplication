package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Util.MasterApi;

public class SIgnIn extends AppCompatActivity {
    private Button SignInButton;
    private TextView SignUpText;
    private TextView useremail;
    private TextView userpass;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_ign_in);

        SignInButton = findViewById(R.id.SignIn);
        SignUpText = findViewById(R.id.signUpNav);
        useremail = findViewById(R.id.userName);
        userpass = findViewById(R.id.password);

        findViewById(R.id.SignInLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                return false;
            }
        });

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ////SIGN IN LOGIC
                firebaseAuth = FirebaseAuth.getInstance();
                String email = useremail.getText().toString().trim();
                String password = userpass.getText().toString().trim();
                if (TextUtils.isEmpty(email) || (!isEmailValid(email)) || TextUtils.isEmpty(password) || password.length()<6){
                    Toast.makeText(SIgnIn.this, "Please Enter Valid Email Address and Password", Toast.LENGTH_SHORT).show();
                }else{
                    signInfunc(email,password);
                }

            }
        });

        SignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //CREATE ACCOUNT NAVIGATE
                Intent intent = new Intent(SIgnIn.this,CreateAccount.class);
                startActivity(intent);
            }
        });

    }

    private void signInfunc(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SIgnIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String Uid = firebaseAuth.getCurrentUser().getUid();
                            MasterApi masterApi = MasterApi.getInstance();
                            masterApi.setUserID(Uid);
                            //Get Name from DataStore for id
                            Intent intent = new Intent(SIgnIn.this,Home.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SIgnIn.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }



    boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return true;
    }
}
