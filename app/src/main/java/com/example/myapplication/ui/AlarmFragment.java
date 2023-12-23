package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.CommunityFragment;
import com.example.myapplication.R;
import com.example.myapplication.acalarm;
import com.example.myapplication.locationalarm;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AlarmFragment extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView alarm;
    private acalarm activityalarm;
    private locationalarm locationalarm;
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_alarm);
        alarm = findViewById(R.id.alarmselect);
        activityalarm= new acalarm();
        locationalarm = new locationalarm();
        // 하단바를 눌렀을 때 프래그먼트가 변경되게 함
        alarm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                if (item.getItemId() == R.id.locationalarm) {
                    setFragment(0);
                } else if (item.getItemId() == R.id.activityalarm) {
                    setFragment(1);
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
                fragmentTransaction.replace(R.id.alarm_frame, locationalarm).commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.alarm_frame, activityalarm).commit();
                break;
        }
    }
}
