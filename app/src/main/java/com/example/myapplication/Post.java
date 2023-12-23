package com.example.myapplication;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Post {

    private String imageUrl;
    private String documentId;
    private String nickname;
    private String title;
    private String contents;
    @ServerTimestamp //1시간 58분 40초  이게 뭔지 모르겠음
    private Date date;

    public Post() {
    }

    public Post(String documentId,String nickname, String title, String contents) {
        this.documentId = documentId;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
    } //constructor
    public Post(String documentId,String nickname, String title, String contents, String URL) {
        this.documentId = documentId;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.imageUrl = URL;
    } //constructor
    public String getImageUrl() { return  imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl;}

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getNicname(){
        return  nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    } //getter setter

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentId='" + documentId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date=" + date +
                '}';
    }
    private LatLng position;

    public Post(LatLng position, String title) {
        this.position = position;
        this.title = title;
    }

    public LatLng getPosition() {
        return position;
    }

}
