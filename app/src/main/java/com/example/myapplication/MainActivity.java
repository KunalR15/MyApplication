package com.example.myapplication;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    FragmentManager fm;
    private static int SPLASH_TIME_OUT =500;
    private static int SPLASH_TIME_OUT1 =3500;
    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        Button getStarted ;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                textView = (TextView) findViewById(R.id.welcome);
                textView.setAlpha(1);
                Animation animation1 =
                        AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.welcomeanim);
                textView.startAnimation(animation1);

            }
        },SPLASH_TIME_OUT);


        getStarted = (Button) findViewById(R.id.getStarted);
        getStarted.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(MainActivity.this,Home.class);
                //startActivity(intent);

                Intent intent = new Intent(MainActivity.this,SIgnIn.class);
                startActivity(intent);
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return true;
    }

}


