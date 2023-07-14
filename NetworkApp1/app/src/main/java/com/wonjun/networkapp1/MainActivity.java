package com.wonjun.networkapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView txtUserId;
    TextView txtId;
    TextView txtTitle;
    TextView txtBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserId = findViewById(R.id.txtUserId);
        txtId = findViewById(R.id.txtId);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);

        // 네트워크 통신을 위해 Volley 준비
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        //주소 요청 GET
        //https://jsonplaceholder.typicode.com/posts/1

        // Request요청을 만들어야 한다. 값 1개를 가져올때 사용하는 방법
        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.GET,
            "https://jsonplaceholder.typicode.com/posts/1",
            null, //보낼 값
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // 1. 데이터를 가져오는 작업
                    try {
                        int userId = response.getInt("userId");
                        int id = response.getInt("id");
                        String title = response.getString("title");
                        String body = response.getString("body");


                        // 2. 화면에 보여주는 작업
                        txtUserId.setText(userId + "");
                        txtId.setText(id + "");
                        txtTitle.setText(title);
                        txtBody.setText(body);

                    } catch (JSONException e) {
                        // 오류 관련해서 유저한테 알려주는 구문 작성하면 된다.
                        Log.i("MAIN HTTP ERROR", e.toString());
                        Toast.makeText(MainActivity.this, "네트워크 통신중 문제 발생", Toast.LENGTH_SHORT).show();

                        return;
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
        );

        // 값 여러개 가져오는 방법
        JsonArrayRequest request1 = new JsonArrayRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject(2);

                            int userId = jsonObject.getInt("userId");
                            int id = jsonObject.getInt("id");
                            String title = jsonObject.getString("title");
                            String body = jsonObject.getString("body");


                            // 2. 화면에 보여주는 작업
                            txtUserId.setText(userId + "");
                            txtId.setText(id + "");
                            txtTitle.setText(title);
                            txtBody.setText(body);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        
                    }
                }
        );


        //네트워크 통신 시켜라.
        queue.add(request1);

    }
}