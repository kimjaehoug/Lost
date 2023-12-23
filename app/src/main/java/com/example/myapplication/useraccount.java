package com.example.myapplication;

public class useraccount {

    private String nickname;

    public useraccount(){
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }


    public String getIdToken() {return idToken;}

    public void setIdToken(String idToken){
        this.idToken = idToken;
    }
    private String idToken;

    public String getEmailId(){return emailId;}
    public void setEmailId(String emailId){this.emailId = emailId;}
    private String emailId;


    public String getPassword(){return password;}
    public void setPassword(String strPwd){this.password = password;}
    private String password;

}

