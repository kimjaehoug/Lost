package com.example.myapplication;

import com.google.firebase.firestore.ServerTimestamp;
public class Chat{
private String documentId;
private String nickname;
private String title;
private String othernickname;
private String contents;

public Chat(){
        }

public Chat(String documentId,String nickname,String title,String contents){
        this.documentId=documentId;
        this.nickname=nickname;
        this.title=title;
        this.contents=contents;
        } //constructor

public Chat(String nickname,String othernickname){
        this.othernickname=othernickname;
        this.nickname=nickname;
        } //constructor

public String getDocumentId(){
        return documentId;
        }

public void setDocumentId(String documentId){
        this.documentId=documentId;
        }
public void setOthernickname(String othernickname){
    this.othernickname=othernickname;
}
public String getOthernickname(String othernickname){
    return othernickname;
}
public String getNicname(){
        return nickname;
        }

public String getTitle(){
        return title;
        }

public void setTitle(String title){
        this.title=title;
        }

public String getContents(){
        return contents;
        }

public void setContents(String contents){
        this.contents=contents;
        } //getter setter

@Override
public String toString(){
        return"Post{"+
        "documentId='"+documentId+'\''+
        ", nickname='"+nickname+'\''+
        ", title='"+title+'\''+
        ", contents='"+contents+'\''+
        '}';
        }
        }