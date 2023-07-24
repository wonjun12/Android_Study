package com.wonjun.training13_postingapi.api;

import com.wonjun.training13_postingapi.model.PostRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface PostApi {
    @GET("/board/follow")
    Call<PostRes> getAllPost(@Header("Authorization") String token, @Query("offset") int offset, @Query("limit") int limit);
}
