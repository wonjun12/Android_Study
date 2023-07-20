package com.wonjun.memoapp.api;

import android.content.Context;

import com.wonjun.memoapp.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    public static Retrofit retrofit;

    public static Retrofit getRetrofitClient(Context context){
        if(retrofit == null){
            // 네트워크 통신한, 로그를 확인하는 코드
            // http 로깅 셋팅
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 네트워크 연결시키는 코드
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES) //네트워크 연결 하는 시간을 최대 1분으로 설정
                    .readTimeout(1, TimeUnit.MINUTES) // 네트워크를  읽는데 시간을 최대 1분으로 설정
                    .writeTimeout(1, TimeUnit.MINUTES) //
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
