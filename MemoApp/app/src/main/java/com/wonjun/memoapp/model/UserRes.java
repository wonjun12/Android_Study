package com.wonjun.memoapp.model;

// HTTP 요청하고 데이터를 받을때에도 따로 공간이 있어야한다.
public class UserRes {
    private String result;
    private String jwt_koken;
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getJwt_koken() {
        return jwt_koken;
    }

    public void setJwt_koken(String jwt_koken) {
        this.jwt_koken = jwt_koken;
    }
}
