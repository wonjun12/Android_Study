package com.wonjun.tabbarbottomtabbar.api;

import com.wonjun.tabbarbottomtabbar.model.PostRes;
import com.wonjun.tabbarbottomtabbar.model.ResultRes;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostApi {
    @GET("/board/follow")
    Call<PostRes> getAllPost(@Header("Authorization") String token, @Query("offset") int offset, @Query("limit") int limit);

    @POST("/board/{postId}/like")
    Call<ResultRes> setLikePost(@Header("Authorization") String token, @Path("postId") int postId);

    @DELETE("/board/{postId}/like")
    Call<ResultRes> delListPost(@Header("Authorization") String token, @Path("postId") int postId);

    @Multipart
    @POST("/board")
    Call<ResultRes> addPost(@Header("Authorization") String token,
            @Part MultipartBody.Part photo, //파일을 보낼때 사용
            @Part("content")RequestBody content //내용을 보낼때 사용
            );
}
