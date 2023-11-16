package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class register_finish_rr extends AppCompatActivity {
    Button nextview;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_finish);
        nextview = findViewById(R.id.register_button);
        nextview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_finish_rr.this, lostlogin.class);
                startActivity(intent);
            }
        });
    }
}
