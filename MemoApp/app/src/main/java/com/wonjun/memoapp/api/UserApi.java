package com.wonjun.memoapp.api;

import com.wonjun.memoapp.model.User;
import com.wonjun.memoapp.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// 유저 관련 API
public interface UserApi {

    // 회원 가입 API
    @POST("/user/create") // 1. 경로 설정
    //리턴타입 함수이름(파라미터)
        // 저장할 공간의 JSON 클래스가 필요하다. 따로 클래스를 만들자.
    Call<UserRes> register(@Body User user);
    //서버로 응답받는 데이터를 리턴타입으로 설정한다. Class를 만들자.
}
