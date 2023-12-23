package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText mTitle, mContents;
    private String nickname;
    private double longitude = 0;
    private double latitude = 0;
    final static int TAKE_PICTURE = 1;
    private String postId;
    private Uri image;
    Button Camera;
    ImageView miribogi;
    Context context = this;

    Bitmap sendimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Camera = findViewById(R.id.capture_mode);
        miribogi = findViewById(R.id.showpicture);
        Intent intent = getIntent();
        if (intent != null) {
            longitude = intent.getDoubleExtra("longitude", 0);
            latitude = intent.getDoubleExtra("latitude", 0);
        }
        mTitle = findViewById(R.id.post_title_edit);
        mContents = findViewById(R.id.post_contents_edit);

        findViewById(R.id.post_save_button).setOnClickListener(this);

        if (mAuth.getCurrentUser() != null) {
            mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                nickname = task.getResult().getString("nickname");
                                Log.d("Firestore", "Nickname retrieved successfully: " + nickname);
                            } else {
                                Log.e("Firestore", "Error retrieving nickname: " + task.getException());
                            }
                        }
                    });
        }
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (mAuth.getCurrentUser() != null) {
            postId = mStore.collection("posts").document().getId();
            String title = mTitle.getText().toString();
            String contents = mContents.getText().toString();

            // 필수 필드가 비어있는지 확인
            if (title.isEmpty() || contents.isEmpty()) {
                Toast.makeText(this, "제목, 내용 또는 닉네임이 비어 있습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put("documentId", mAuth.getCurrentUser().getUid());
            data.put("nickname", nickname);
            data.put("title", title);
            data.put("contents", contents);
            data.put("long", longitude);
            data.put("latit", latitude);
            data.put("timestamp", FieldValue.serverTimestamp());

            mStore.collection("posts").document(postId).set(data, SetOptions.merge())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PostActivity.this, "게시물이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PostActivity.this, "게시물 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            // 이미지 업로드 및 저장
            uploadImageAndSavePost(postId, title, contents);
        }
    }

    private void uploadImageAndSavePost(final String postId, final String title, final String contents) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + postId + ".jpg");
        UploadTask uploadTask = storageRef.putFile(getImageUri(context, sendimage));

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return storageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                if (downloadUri != null) {
                    String imageUrl = downloadUri.toString();

                    // 이미지 URL을 Firestore에 저장
                    savePostWithImage(postId, title, contents, imageUrl);
                }
            } else {
                // 실패 처리
            }
        });
    }

    private void savePostWithImage(String postId, String title, String contents, String imageUrl) {
        Map<String, Object> data = new HashMap<>();
        data.put("imageUrl", imageUrl);

        mStore.collection("posts").document(postId).set(data, SetOptions.merge())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(PostActivity.this, "게시물이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(PostActivity.this, "게시물 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        miribogi.setImageBitmap(bitmap);
                        sendimage = bitmap;
                    }
                }
                break;
        }
    }
}
