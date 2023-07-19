package com.wonjun.training12_papagoapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wonjun.training12_papagoapi.config.Config;
import com.wonjun.training12_papagoapi.model.Papago;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RadioGroup radioGroup;
    EditText editText;
    Button button;
    TextView txtResult;

    ArrayList<Papago> papagoArrayList = new ArrayList<>();
    String target;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        radioGroup = findViewById(R.id.radioGroup);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        txtResult = findViewById(R.id.txtTranslatedText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 어떤 언어로 번역할지 대한 정보를 가져온다.
                // 눌려진 라디오 버튼 정보를 가져온다.
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                if(radioButtonId == R.id.radioButton1){
                    target = "en";
                }else if(radioButtonId == R.id.radioButton2){
                    target = "zh-CN";
                }else if(radioButtonId == R.id.radioButton3){
                    target = "zh-TW";
                }else if(radioButtonId == R.id.radioButton4){
                    target = "th";
                }else{
                    return;
                }


                // 2. 에디트 텍스트에, 유저가 작성한 글을 가져온다.
                text = editText.getText().toString().trim();

                if(text.isEmpty()){
                    return;
                }

                // 3. 파파고 API를 호출한다. 결과를 화면에 보여준다.
                String source = "ko";
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                // url 및 데이터 설정
                String url = "https://openapi.naver.com/v1/papago/n2mt";
                JSONObject body = new JSONObject(); //보낼 데이터
                try {
                    body.put("source", source);
                    body.put("target", target);
                    body.put("text", text);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                progressBar.setVisibility(View.VISIBLE);

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        body,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //네이버로 부터 받은 데이터를 처리하고 화면에 표시
                                try {
                                    String translatedText = response.getJSONObject("message").getJSONObject("result").getString("translatedText");
                                    txtResult.setText(translatedText);

                                    Papago papago = new Papago(text, translatedText, target);

                                    papagoArrayList.add(papago);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                ){ // 헤더 설정

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<>();
                        //header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                        header.put("X-Naver-Client-Id", Config.NAVER_CLIENT_ID);
                        header.put("X-Naver-Client-Secret", Config.NAVER_CLIENT_SECRET);

                        return header;
                    }
                };

                queue.add(request);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.papagoList){
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("papagoArrayList", papagoArrayList);

            startActivity(intent);
        }

        return true;
    }
}