package com.wonjun.tabbarbottomtabbar.api;

import com.wonjun.tabbarbottomtabbar.model.ResultRes;
import com.wonjun.tabbarbottomtabbar.model.User;
import com.wonjun.tabbarbottomtabbar.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/user/register")
    Call<ResultRes> registerUser(@Body User user);

    @POST("/user/login")
    Call<UserRes> loginUser(@Body User user);
}
