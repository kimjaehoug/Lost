package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class locationalarm extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationModel> notificationList;
    int truefalse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.locationalarm, container, false);

        Bundle args = getArguments();
        if (args != null) {
            truefalse = args.getInt("go", 1);
        }

        // 알림 목록 데이터 생성
        notificationList = new ArrayList<>();

        // RecyclerView 초기화
        recyclerView = view.findViewById(R.id.locationlist); // view에서 findViewById를 호출해야 합니다.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Adapter 설정
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
        if(truefalse == 0){
            NotificationModel asdf = new NotificationModel("분실물 발견", "근처에서 분실물이 발견 되었습니다.");
            addNotification(asdf);
        }

        return view;
    }

    // 알림 목록에 항목 추가
    public void addNotification(NotificationModel notification) {
        if (notificationList != null) {
            notificationList.add(notification);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }
}