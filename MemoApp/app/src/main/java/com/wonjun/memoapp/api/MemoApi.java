package com.wonjun.memoapp.api;

import com.wonjun.memoapp.model.Memo;
import com.wonjun.memoapp.model.MemoList;
import com.wonjun.memoapp.model.ResultRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MemoApi {
    
    //get 방식
    @GET("/memo")
    // 헤더에 값 추가하는 방법
    Call<MemoList> getMemoList(@Header("Authorization") String token);
                                            // key      : value


    // 메모 만들기
    @POST("/memo")
    // 응답의 값이 result만 오기도하고, 메모 수정, 삭제 등 일관성 있게 result값만 오는 경우가 많으니,
        // result만 받는 클래스를 만들자.
    Call<ResultRes> addMemo(@Header("Authorization") String token, @Body Memo memo);
                // 헤더와 바디 같이 보내기

    // 메모 수정
    @PUT("/memo/{memoId}") // 경로 설정에서 memoId의 id를 path로 추가할 수 잇음
    Call<ResultRes> editMemo(@Header("Authorization") String token, @Body Memo memo, @Path("memoId") int memoId);

    // 메모 삭제
    @DELETE("/memo/{memoId}")
    Call<ResultRes> delMemo(@Path("memoId") int memoId, @Header("Authorization") String token);
    
}
