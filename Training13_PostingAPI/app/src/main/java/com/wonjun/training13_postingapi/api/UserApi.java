package com.wonjun.training13_postingapi.api;

import com.wonjun.training13_postingapi.model.ResultRes;
import com.wonjun.training13_postingapi.model.User;
import com.wonjun.training13_postingapi.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/user/register")
    Call<ResultRes> registerUser(@Body User user);

    @POST("/user/login")
    Call<UserRes> loginUser(@Body User user);
}
