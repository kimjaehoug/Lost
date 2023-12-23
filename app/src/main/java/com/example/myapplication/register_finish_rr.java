package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class register_finish_rr extends AppCompatActivity {
    Button nextview;
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_finish);
        mStore.collection("Users").document(mAuth.getCurrentUser().getUid());
        String postId = mAuth.getCurrentUser().getUid();
        String nickname = getIntent().getStringExtra("nickname");
        Map<String, Object> data = new HashMap<>();
        data.put("nickname", nickname);
        mStore.collection("Users").document(postId).set(data, SetOptions.merge());
        nextview = findViewById(R.id.register_button);
        nextview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent asdf = new Intent(register_finish_rr.this, lostlogin.class);
                startActivity(asdf);
            }
        });
    }
}
