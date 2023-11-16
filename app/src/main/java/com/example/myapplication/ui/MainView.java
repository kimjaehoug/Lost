package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    private AlarmFragment alarmFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView main_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);

        // 프래그먼트 초기화
        mapsfragment = new mapsfragment();
        communityFragment = new CommunityFragment();
        myPageFragment = new MyPageFragment();
        alarmFragment = new AlarmFragment();

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
                } else if (item.getItemId() == R.id.bottom_community) {
                    setFragment(1);
                } else if (item.getItemId() == R.id.bottom_mypage) {
                    setFragment(2);
                } else if (item.getItemId() == R.id.bottom_alarm) {
                    setFragment(3);
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
                fragmentTransaction.replace(R.id.main_frame, myPageFragment).commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.main_frame, alarmFragment).commit();
                break;
        }
    }
}
