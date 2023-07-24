package com.wonjun.training13_postingapi.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonjun.training13_postingapi.MainActivity;
import com.wonjun.training13_postingapi.R;
import com.wonjun.training13_postingapi.api.NetworkClient;
import com.wonjun.training13_postingapi.api.UserApi;
import com.wonjun.training13_postingapi.config.Config;
import com.wonjun.training13_postingapi.model.User;
import com.wonjun.training13_postingapi.model.UserRes;

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
                if(password.length() < 4){
                    return;
                }

                Retrofit retrofit = NetworkClient.getRetrofitClient(UserLoginActivity.this);
                UserApi api = retrofit.create(UserApi.class);
                User user = new User(email,password);
                Call<UserRes> call = api.loginUser(user);
                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        if(response.isSuccessful()){
                            UserRes res = response.body();

                            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Config.ACCESS_TOKEN, "Bearer " + res.getAccess_token());
                            editor.apply();

                            Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

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
}