package com.wonjun.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class UserRegisterActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    EditText editNickname;
    Button btnRegister;
    TextView txtToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editNickname = findViewById(R.id.editNickname);
        btnRegister = findViewById(R.id.btnRegister);
        txtToLogin = findViewById(R.id.txtToLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String nickname = editNickname.getText().toString().trim();

                // https://hydok.tistory.com/36
                // 이메일 형식 체크
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(!pattern.matcher(email).matches()){
                    return;
                }

                if(password.length() < 4){
                    return;
                }

                if(nickname.isEmpty()){
                    return;
                }

                showProgress();

                // 회원가입 API 요청
                // 1. 레트로핏 변수 생성 (api폴더 NetworkClient.class 생성하기)
                Retrofit retrofit = NetworkClient.getRetrofitClient(UserRegisterActivity.this);

                // 2. API 패키지의 인터페이스 생성
                //      => api 폴더에서 인터페이스 작성
                //    인터페이스 작성 후 API를 만들자.
                UserApi api = retrofit.create(UserApi.class);

                // 3. 보낼 데이터 준비
                User user = new User(email, password, nickname);

                // 4. 요청/응답
                Call<UserRes> call = api.register(user);
                call.enqueue(new Callback<UserRes>() { //실제 요청하는 로직
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 네트워크 응답에 성공했을때
                        dismissProgress();

                        // 200 ok 확인
                        if(response.isSuccessful()){ //200이면 true로 적용된다.
                            
                            UserRes res = response.body(); // 응답 데이터

                            Log.i("MEMO APP", res.getJwt_koken());
                            Log.i("MEMO APP", res.getResult());

                            // 회원가입에서 받은 억세스토큰은 앱내에서 저장한다.
                            SharedPreferences sp = getSharedPreferences(Config.PREFFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Config.ACCESS_TOKEN, res.getJwt_koken());
                            editor.apply();

                            // 회원가입이 정상적으로 끝났으니,
                            // Main Activity를 실행하자.
                            Intent intent = new Intent(UserRegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();
                        }else{
                            // 200 ok가 아닐경우 실행
                            Log.i("MEMO APP", response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        // 네트워크 응답에 실패 했을때
                        dismissProgress();
                    }
                });
                



            }
        });

        txtToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }



    // 데이터 생성 및 수정, 삭제 등 데이터에 직집적으로 영향을 주는 공간의 경우
    // 갑작스런 종료가 있으면 오류가 생길수 있으니 못하게 하는 함수를 작성하자.
    Dialog dialog;
    void showProgress(){
        dialog = new Dialog(this);
        dialog.setContentView(new ProgressBar(this));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    void dismissProgress(){
        dialog.dismiss();
    }

}