package com.wonjun.memoapp.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.memoapp.R;
import com.wonjun.memoapp.api.MemoApi;
import com.wonjun.memoapp.api.NetworkClient;
import com.wonjun.memoapp.config.Config;
import com.wonjun.memoapp.model.Memo;
import com.wonjun.memoapp.model.ResultRes;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;

    Button btnDate;
    String date = "";
    Button btnTime;
    String time = "";
    Button btnSave;

    Memo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editContent = findViewById(R.id.editContent);
        editTitle = findViewById(R.id.editTitle);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnSave = findViewById(R.id.btnSave);

        memo = (Memo) getIntent().getSerializableExtra("memo");

        editTitle.setText(memo.getTitle());
        editContent.setText(memo.getContent());

        String [] memoDate = memo.getMemoDate().split(" ");
        date = memoDate[0];
        time = memoDate[1].substring(0, memoDate[1].length() - 3);
        btnDate.setText(date);
        btnTime.setText(time);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 기본값 설정을 위해 현재 날짜를 들고옴
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(
                        EditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                // i : 년
                                // i1 : 월 (0 부터 시작)
                                // i2 : 일
                                int month = i1 + 1;
                                String strMonth = (month < 10)? "0" + month: "" + month;

                                String strDay = (i2 < 10)?  "0" + i2: "" + i2;

                                date = i + "-" + strMonth + "-" + strDay;

                                btnDate.setText(date);
                            }
                        },
                        calendar.get(Calendar.YEAR), //기본 값 설정 (현재 날짜를 들고오는게 좋음)
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                dialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();


                TimePickerDialog dialog = new TimePickerDialog(
                        EditActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                                String strHour = (i < 10)? "0" + i : "" + i;
                                String strMinute = (i1 < 10)? "0" + i1 : "" + i1;

                                time = strHour + ":" + strMinute;

                                btnTime.setText(time);
                            }
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                );

                dialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editContent.getText().toString().trim();
                String title = editTitle.getText().toString().trim();

                if(content.isEmpty() || title.isEmpty()){
                    Snackbar.make(
                            btnSave,
                            "제목과 내용을 입력하세요.",
                            Snackbar.LENGTH_SHORT
                    ).show();
                    return;
                }

                if(date.isEmpty() || time.isEmpty()){
                    Snackbar.make(
                            btnSave,
                            "날짜와 시간을 선택하세요.",
                            Snackbar.LENGTH_SHORT
                    ).show();
                    return;
                }

                showProgress();

                Retrofit retrofit = NetworkClient.getRetrofitClient(EditActivity.this);

                MemoApi api = retrofit.create(MemoApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString(Config.ACCESS_TOKEN, "");

                /*
                memo.setTitle(title);
                memo.setContent(content);
                memo.setMemoDate(date + " " + time);*/

                Memo memo = new Memo(title, content, date + " " + time);

                Call<ResultRes> call = api.editMemo("Bearer " + token, memo, EditActivity.this.memo.getId());
                call.enqueue(new Callback<ResultRes>() {
                    @Override
                    public void onResponse(Call<ResultRes> call, Response<ResultRes> response) {
                        dismissProgress();
                        if(response.isSuccessful()){
                            finish();
                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResultRes> call, Throwable t) {
                        dismissProgress();
                    }
                });
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