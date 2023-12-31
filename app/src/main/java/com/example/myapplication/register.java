package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어 베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText mEtEmail, mEtPwd;
    private Button mBtnRegister;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //회원가입 처리 시작 이메일과 패스워드 칸에 있는 내용을 읽음
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                // Firebase Auth 전환
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            useraccount account = new useraccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);

                            // Intent로부터 닉네임을 가져옴
                            String nickname = getIntent().getStringExtra("nickname");
                            account.setNickname(nickname);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                            Toast.makeText(register.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(register.this, register_finish_rr.class);
                            intent.putExtra("nickname", nickname);
                            startActivity(intent);
                        }else{
                            Toast.makeText(register.this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}
