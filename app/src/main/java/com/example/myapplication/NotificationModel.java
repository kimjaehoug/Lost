package com.example.myapplication;

// NotificationModel.java
public class NotificationModel {
    private String title;
    private String content;

    public NotificationModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

