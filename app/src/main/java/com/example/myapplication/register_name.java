package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register_name extends AppCompatActivity {
    private EditText mEtNickname;
    private Button mNextCheck;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("UserAccount");

        mEtNickname = findViewById(R.id.et_nickname);
        mNextCheck = findViewById(R.id.next_name);


        mNextCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = mEtNickname.getText().toString().trim();


                if (!nickname.isEmpty()) {
                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                    if (firebaseUser != null) {
                        String userId = firebaseUser.getUid();

                        // 닉네임을 Firebase 실시간 데이터베이스에 저장
                        mDatabaseRef.child(userId).child("nickname").setValue(nickname)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Intent를 통해 닉네임을 register 액티비티로 전달
                                            Intent intent = new Intent(register_name.this, register.class);
                                            intent.putExtra("nickname", nickname);
                                            startActivity(intent);
                                        } else {
                                            //인터넷이 안될때.
                                            Log.e("Firestore", "인터넷이 안됩니다. ");
                                        }
                                    }
                                });
                    }
                } else {
                    Log.e("error","faild nickname"); //오류시 처리
                    // 닉네임이 비어있는 경우 사용자에게 알림을 표시하거나 처리
                }
            }
        });
    }
}
