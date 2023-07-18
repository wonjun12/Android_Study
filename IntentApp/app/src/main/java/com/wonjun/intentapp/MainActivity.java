package com.wonjun.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectContact();
                //openWebPage("https://www.naver.com");
                //composeSMS("010-7594-7007");
                composeEmail(new String[]{"wjdaks333@naver.com"}, "테스트");
            }
        });
    }


    // 연락처 선택하는 액티비티 띄우기
    // TODO: 2023-07-18 usb로 핸드폰을 연결해서 테스트 할 수있다. 
    public void selectContact(){
        // TODO: 2023-07-18 연락처
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        startActivity(intent);
    }

    void openWebPage(String url){
        // TODO: 2023-07-18 웹브라우저.
        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }

    // TODO: 2023-07-18 SMS앱 열기
    void composeSMS(String phone){
        Uri uri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    // TODO: 2023-07-18 이메일
    void composeEmail(String[] address, String subject){
        Uri uri = Uri.parse("mailto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(uri);
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(intent);

    }


}