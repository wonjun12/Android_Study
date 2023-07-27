package com.wonjun.training14_googleapi_study.api;

import com.wonjun.training14_googleapi_study.model.PlaceRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceApi {
    @GET("/maps/api/place/nearbysearch/json")
    Call<PlaceRes> getPlaceList(@Query("language") String language,
                                @Query("keyword") String keyword,
                                @Query("type") String type,
                                @Query("radius") int radius,
                                @Query("location") String location,
                                @Query("key") String key);

    @GET("/maps/api/place/nearbysearch/json")
    Call<PlaceRes> addPlaceList(@Query("pagetoken") String pagetoken, @Query("key") String key);

//    @GET("/maps/api/place/nearbysearch/json")
//    Call<PlaceRes> getPlaceList(@Query("language") String language,
//                                @Query("type") String type,
//                                @Query("radius") int radius,
//                                @Query("location") String location,
//                                @Query("key") String key);
}
