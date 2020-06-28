package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ResolutionDimension;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Util.MasterApi;

public class CreateAccount extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser currentUser;
    private TextView signInNavText;

    //Firesore con
    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private ProgressBar progressBar;
    private Button createAccBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth =FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_account);
        signInNavText = findViewById(R.id.signInNav);

        progressBar = findViewById(R.id.sign_up_progress);
        nameEditText = findViewById(R.id.UserName);
        emailEditText = findViewById(R.id.userEmail);
        passwordEditText  = findViewById(R.id.UserPassword);
        createAccBtn = findViewById(R.id.signUp);

        findViewById(R.id.SignUpLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                return false;
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser !=null){
                    // user already logged in
                }else{
                    //No user Yet
                }
            }
        };

        signInNavText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // nav to signIn
                Intent intent = new Intent(CreateAccount.this,SIgnIn.class);
                startActivity(intent);
            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Create code
                if(!TextUtils.isEmpty(emailEditText.getText().toString())
                        && !TextUtils.isEmpty(passwordEditText.getText().toString())
                        && !TextUtils.isEmpty(nameEditText.getText().toString())){
                    String email = emailEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    String name = nameEditText.getText().toString().trim();

                    createUserEmailAccount(email,password,name);

                }else{
                    Toast.makeText(CreateAccount.this,
                            "Please Enter Valid Name, Email Address and Password",
                                    Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    private void createUserEmailAccount (String email, String password, final String name){

        if (TextUtils.isEmpty(email) || (!isEmailValid(email))){
            Toast.makeText(this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        if (password.length()<6){
            Toast.makeText(this, "Password should be more Than 6 characters", Toast.LENGTH_SHORT).show();
        }

        if(!TextUtils.isEmpty(email) && (isEmailValid(email))
        && !TextUtils.isEmpty(password)
        && password.length()>6)
        {
            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("message", "createUserWithEmail:success");
                                currentUser = firebaseAuth.getCurrentUser();

                                assert currentUser !=null;
                                final String currentUserID = currentUser.getUid();

                                //User Map for user account

                                Map<String, String>usrobj=new HashMap<>();
                                usrobj.put("serID",currentUserID);
                                usrobj.put("UserName",name);

                                //save to Database

                                collectionReference.add(usrobj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {

                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if(Objects.requireNonNull(task.getResult()).exists()){
                                                                    progressBar.setVisibility(View.VISIBLE);
                                                                    String name = task.getResult()
                                                                            .getString("name");
                                                                    MasterApi masterApi = MasterApi.getInstance();
                                                                    masterApi.setUserID(currentUserID);
                                                                    masterApi.setName(name);
                                                                    Intent intent = new Intent(CreateAccount.this,Home.class);
                                                                    intent.putExtra("Name",name);
                                                                    intent.putExtra("UserId",currentUserID);
                                                                    startActivity(intent);

                                                                }else{
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                }
                                                            }
                                                        });

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });






                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Message", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(CreateAccount.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }else{

        }

    }


    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return true;
    }

    boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Override
    protected void onStart() {
        super.onStart();

        currentUser= firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

}
