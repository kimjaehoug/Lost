package com.example.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private String rname;
    private String rnickname;
    private String rcontent;
    private String rImage;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communityplus);
        TextView name = findViewById(R.id.co_name);
        TextView nickname = findViewById(R.id.co_nickname);
        TextView co_content = findViewById(R.id.co_content);
        ImageView imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        rname = intent.getStringExtra("title");
        rnickname = intent.getStringExtra("nickname");
        rcontent = intent.getStringExtra("contents");
        rImage = intent.getStringExtra("URL");
        name.setText(rname);
        nickname.setText(rnickname);
        co_content.setText(rcontent);
        Picasso.get().load(rImage).into(imageView);


    }
}
