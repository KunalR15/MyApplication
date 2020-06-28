package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Util.MasterApi;

public class Home extends AppCompatActivity {

    float x1,y1,x2,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String Uid = MasterApi.getInstance().getUserID();
        String Uname = MasterApi.getInstance().getName();
        RecyclerView feeds =(RecyclerView)findViewById(R.id.feedList);
        feeds.setLayoutManager(new LinearLayoutManager(this));
        String[] Names ={"Image1","Image2","Image3","Image4","Image5","Image6"};
        feeds.setAdapter(new FeedAdapter(Names));


        final ImageButton menudragger;
        menudragger  = findViewById(R.id.MenuDragger);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.addBox:
                        startActivity(new Intent(getApplicationContext()
                                ,AddActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext()
                                ,UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext()
                                ,SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        menudragger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible = bottomNavigationView.getVisibility();
                Log.i("TAG", String.valueOf(visible));
                if (visible != 0){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    menudragger.animate().rotation(menudragger.getRotation() + 360).setDuration(500).start();
                    bottomNavigationView.animate().translationY(0).setDuration(500).start();
                }else{
                    menudragger.animate().rotation(menudragger.getRotation() - 360).setDuration(500).start();
                    bottomNavigationView.animate().translationY(bottomNavigationView.getHeight()).setDuration(1000).start();
                    bottomNavigationView.setVisibility(View.GONE);
                }
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
