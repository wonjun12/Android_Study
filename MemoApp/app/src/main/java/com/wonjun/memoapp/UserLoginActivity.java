package com.wonjun.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.memoapp.api.NetworkClient;
import com.wonjun.memoapp.api.UserApi;
import com.wonjun.memoapp.config.Config;
import com.wonjun.memoapp.model.User;
import com.wonjun.memoapp.model.UserRes;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserLoginActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    Button btnLogin;
    TextView txtToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtToRegister = findViewById(R.id.txtToRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(!pattern.matcher(email).matches()){
                    return;
                }

                if(password.length() <  4){
                    return;
                }
                showProgress();
                // 네트워크 연결
                Retrofit retrofit = NetworkClient.getRetrofitClient(UserLoginActivity.this);

                // URL API 호출
                // UserApi.Class에 호출할 url 추가
                    // 추가 후 API 사용하자.
                UserApi api = retrofit.create(UserApi.class); //UserApi 메모리에 생성해서 실행할 준비
                User user = new User(email, password); // 로그인에 필요한 클래스를 만든다. (JSON의 형태라고 생각하면 됨)
                Call<UserRes> call = api.login(user); // api 요청 시작하면서 메모리 저장
                call.enqueue(new Callback<UserRes>() { // api 응답에 대해서 로직 작성 
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        dismissProgress();
                        // 네트워크 요청/응답 성공
                            // 에러를 응답받아도 API간의 통신은 성공적이긴 하다.
                        if(response.isSuccessful()){ // 200 OK 통신 일때
                            
                            UserRes res = response.body(); // 데이터 가져오기

                            Log.i("LOGIN LOG", res.getAccess_token());

                            String accessToken = res.getAccess_token();

                            SharedPreferences sp = getSharedPreferences(Config.PREFFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Config.ACCESS_TOKEN, accessToken);
                            editor.apply();

                            Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();
                            
                        }else if(response.code() == 400){ // 특정 통신 코드에 관련해서 처리하고 싶다면
                            // 400코드에 관련해서 처리를  하고 싶다.
                            Snackbar.make(btnLogin,
                                    "이메일, 비밀번호 틀림",
                                    Snackbar.LENGTH_SHORT).show();

                        }else{
                            Snackbar.make(btnLogin,
                                    "서버에 문제가 있음",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        dismissProgress();
                        // 요청 자체가 실패할때
                    }
                });






            }
        });

        txtToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(intent);

                finish();
            }
        });

    }

    Dialog dialog;
    void showProgress(){
        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.setContentView(new ProgressBar(this));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    void dismissProgress(){
        dialog.dismiss();
    }

}