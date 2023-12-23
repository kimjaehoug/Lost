package com.example.myapplication;

public class ChatMessage {
    private String message;
    private String sender;
    private long timestamp;

    public ChatMessage() {
        // 기본 생성자는 Firebase에서 데이터를 가져올 때 필요합니다.
    }

    public ChatMessage(String message, String sender, long timestamp) {
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
