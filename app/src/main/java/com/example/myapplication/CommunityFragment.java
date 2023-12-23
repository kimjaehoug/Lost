package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommunityFragment extends Fragment implements View.OnClickListener, PostAdapter.OnItemClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mPostRecyclerView;
    private PostAdapter mAdapter;
    private List<Post> mDatas;
    private String documentId;
    private String title;
    private String nickname;
    private String contents;
    private String URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_community, container, false);

        mPostRecyclerView = view.findViewById(R.id.main_recyclerview);
        mDatas = new ArrayList<>();

        // 임시 데이터 추가
        view.findViewById(R.id.main_post_edit).setOnClickListener(this);

        // PostAdapter 객체 생성
        mAdapter = new PostAdapter(mDatas);

        // RecyclerView에 어댑터 설정
        mPostRecyclerView.setAdapter(mAdapter);

        // 클릭 리스너 설정
        mAdapter.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadPosts();
    }

    private void loadPosts() {
        mStore.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            mDatas.clear();

                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> data = snap.getData();
                                documentId = snap.getId();
                                title = String.valueOf(data.get("title"));
                                nickname = String.valueOf(data.get("nickname"));
                                contents = String.valueOf(data.get("contents"));
                                URL = String.valueOf(data.get("imageUrl"));
                                Post post = new Post(documentId, nickname, title, contents, URL);
                                mDatas.add(post);
                            }

                            mAdapter.notifyDataSetChanged(); // 데이터 변경 시 어댑터에 알림
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), DetailActivity.class));
    }

    @Override
    public void onItemClick(int position) {
        // RecyclerView의 아이템을 클릭했을 때 실행되는 메서드
        Post clickedPost = mDatas.get(position);
        String documentId = clickedPost.getDocumentId();
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("documentId", documentId);
        intent.putExtra("title", title);
        intent.putExtra("nickname", nickname);
        intent.putExtra("URL",URL);
        intent.putExtra("contents", contents);
        startActivity(intent);
    }
}
