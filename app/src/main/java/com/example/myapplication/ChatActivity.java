package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.chatFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;


public class ChatActivity extends AppCompatActivity {
    private FirebaseListAdapter<ChatMessage> adapter;
    private FirebaseUser currentUser;
    private String chatRoomId;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        chatRoomId = getIntent().getStringExtra("chatRoomId");

        displayChatMessages();
    }

    private void displayChatMessages() {
        ListView listOfMessages = findViewById(R.id.list_of_messages);

        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setLayout(R.layout.message)
                .setQuery(FirebaseDatabase.getInstance().getReference().child("chat").child(chatRoomId), ChatMessage.class)
                .build();

        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
                // UI에 메시지 표시
            }
        };

        listOfMessages.setAdapter(adapter);
    }

    // 전송 버튼 클릭 시 호출
    public void sendMessage() {
        EditText input = findViewById(R.id.input);

        // 메시지 전송
        FirebaseDatabase.getInstance().getReference().child("chat").child(chatRoomId).push()
                .setValue(new ChatMessage(input.getText().toString(), currentUser.getDisplayName(), System.currentTimeMillis()));

        input.setText("");  // 메시지 입력란 비우기
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
