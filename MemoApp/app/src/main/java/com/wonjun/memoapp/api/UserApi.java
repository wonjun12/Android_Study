package com.wonjun.memoapp.api;

import com.wonjun.memoapp.model.ResultRes;
import com.wonjun.memoapp.model.User;
import com.wonjun.memoapp.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;

// 유저 관련 API
public interface UserApi {

    // 회원 가입 API
    @POST("/user/create") // 1. 경로 설정
    //리턴타입 함수이름(파라미터)
        // 저장할 공간의 JSON 클래스가 필요하다. 따로 클래스를 만들자.
    Call<UserRes> register(@Body User user);
    //서버로 응답받는 데이터를 리턴타입으로 설정한다. Class를 만들자.



    // 로그인 API
    @POST("/user/login")
    Call<UserRes> login(@Body User user);
        // POST / GET 등 경로 확인 후
        // 1. 함수이름 설정
        // 2. 필요한 클래스를 새로 만들던가 (User 처럼) 이미 있는 클래스에서 재활용가능하면 재활용 하자.
            // 파라미터에 값 추가
        // 3. 받아주는 리턴 (클래스 작성)

    // 로그아웃 API
    @DELETE("/user/logout")
    Call<ResultRes> logout(@Header("Authorization") String token);
}
