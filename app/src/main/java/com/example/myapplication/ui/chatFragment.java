package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ChatActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class chatFragment extends Fragment {

    private ListView chatRoomListView;
    private ArrayAdapter<String> adapter;
    private List<String> chatRoomList;
    private DatabaseReference chatRoomsRef;
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String nickname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat, container, false);

        chatRoomListView = view.findViewById(R.id.chatroomView);
        chatRoomList = new ArrayList<>();
        chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("chatRooms");


        adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, chatRoomList);
        chatRoomListView.setAdapter(adapter);
            chatRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // 클릭한 채팅방으로 이동
                    String selectedChatRoom = chatRoomList.get(position);
                    Intent intent = new Intent(requireActivity(), ChatActivity.class);
                    intent.putExtra("chatRoomId", selectedChatRoom);
                    startActivity(intent);
                }
            });
        // 데이터 변경을 어댑터에 알리기
        adapter.notifyDataSetChanged();
        createChatRoom("장진우 님과 대화");
        createChatRoom("test 님과 대화");
        return view;
    }

    private void createChatRoom(String chatRoomName) {
        // chatRooms 노드 아래에 새로운 채팅방 생성
        String chatRoomId = chatRoomsRef.push().getKey();
        chatRoomsRef.child(chatRoomId).setValue(chatRoomName);
        chatRoomList.add(chatRoomName);

    }
}
