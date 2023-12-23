package com.example.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Reportview;
import com.example.myapplication.ui.AlarmFragment;
import com.example.myapplication.CommunityFragment;
import com.example.myapplication.MapsActivity;
import com.example.myapplication.ui.MyPageFragment;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainView extends AppCompatActivity {

    private mapsfragment mapsfragment;
    private CommunityFragment communityFragment;
    private MyPageFragment myPageFragment;
    private chatFragment chatFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView main_bottom;
    Button alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.BLACK);


        alarm = findViewById(R.id.alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainView.this, AlarmFragment.class);
                startActivity(intent);
            }
        });

        // 프래그먼트 초기화
        mapsfragment = new mapsfragment();
        communityFragment = new CommunityFragment();
        myPageFragment = new MyPageFragment();
        chatFragment = new chatFragment();

        // 하단바 초기화
        main_bottom = findViewById(R.id.mainebottom);

        // 하단바를 눌렀을 때 프래그먼트가 변경되게 함
        main_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                if (item.getItemId() == R.id.bottom_home) {
                    setFragment(0);
                    getSupportActionBar().setTitle("홈");
                } else if (item.getItemId() == R.id.bottom_community) {
                    setFragment(1);
                    getSupportActionBar().setTitle("커뮤니티");
                } else if (item.getItemId() == R.id.bottom_chat) {
                    setFragment(2);
                    getSupportActionBar().setTitle("채팅");
                } else if (item.getItemId() == R.id.bottom_mypage) {
                    setFragment(3);
                    getSupportActionBar().setTitle("마이페이지");
                }

                return true;
            }
        });
    }

    // 프래그먼트 교체가 일어나는 함수
    public void setFragment(int n) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (n) {
            case 0:
                fragmentTransaction.replace(R.id.main_frame, mapsfragment).commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.main_frame, communityFragment).commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.main_frame, chatFragment).commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.main_frame, myPageFragment).commit();
                break;
        }
    }
}
