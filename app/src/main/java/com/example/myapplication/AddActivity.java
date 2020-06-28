package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final ImageButton menudragger;
        menudragger  = findViewById(R.id.MenuDragger);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.addBox);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.addBox:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,Home.class));
                        overridePendingTransition(0,0);
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
}
