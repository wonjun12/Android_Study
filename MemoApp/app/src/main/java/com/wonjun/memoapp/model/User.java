package com.wonjun.memoapp.model;

public class User {
    // API의 보내는 값들의 이름과 똑같이 설정하자.
    private String email;
    private String password;
    private String nickname;

    //네트워크 용 생성자도 만들어준다.
    public User() {
    }

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
