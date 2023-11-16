package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class register_check extends AppCompatActivity {
    Button nextview;
    private CheckBox masterCheckbox;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_check);
        nextview = findViewById(R.id.next);
        nextview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_check.this, register_name.class);
                startActivity(intent);
            }
        });
        masterCheckbox = findViewById(R.id.allcheck); // 마스터 체크박스
        checkBox1 = findViewById(R.id.check1);
        checkBox2 = findViewById(R.id.check2);
        checkBox3 = findViewById(R.id.check3);

        // 마스터 체크박스의 상태가 변경될 때 이벤트 처리
        masterCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 마스터 체크박스의 상태가 변경되면 모든 체크박스의 상태를 동일하게 설정
                setAllCheckboxesChecked(isChecked);
            }
        });
    }

    // 모든 체크박스의 체크 상태를 동일하게 설정하는 메서드
    private void setAllCheckboxesChecked(boolean isChecked) {
        checkBox1.setChecked(isChecked);
        checkBox2.setChecked(isChecked);
        checkBox3.setChecked(isChecked);
        // 필요에 따라 체크박스의 개수에 맞게 추가
    }
}
