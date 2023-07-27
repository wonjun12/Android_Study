package com.wonjun.tabbarbottomtabbar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.tabbarbottomtabbar.api.NetworkClient;
import com.wonjun.tabbarbottomtabbar.api.UserApi;
import com.wonjun.tabbarbottomtabbar.model.ResultRes;
import com.wonjun.tabbarbottomtabbar.model.User;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRegisterActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    Button btnRegister;
    TextView txtToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtToLogin =  findViewById(R.id.txtToLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
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

                showProgress();

                Retrofit retrofit = NetworkClient.getRetrofitClient(UserRegisterActivity.this);

                UserApi api = retrofit.create(UserApi.class);

                User user = new User(email, password);
                Call<ResultRes> call = api.registerUser(user);
                call.enqueue(new Callback<ResultRes>() {
                    @Override
                    public void onResponse(Call<ResultRes> call, Response<ResultRes> response) {
                        dismissProgress();
                        if(response.isSuccessful()){
                            Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                            startActivity(intent);

                            finish();
                        }else if(response.code() == 400){
                            Snackbar.make(
                                    btnRegister,
                                    "이미 회원가입 되어있음",
                                    Snackbar.LENGTH_SHORT
                            ).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultRes> call, Throwable t) {
                        dismissProgress();

                    }
                });
            }
        });


        txtToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
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