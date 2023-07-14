package com.wonjun.training09_networkapp_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wonjun.training09_networkapp_volley.model.Member;

public class MemberAdd extends AppCompatActivity {

    EditText editName;
    EditText editSalary;
    EditText editAge;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);

        editName = findViewById(R.id.editName);
        editSalary = findViewById(R.id.editSalary);
        editAge = findViewById(R.id.editAge);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String salaryStr = editSalary.getText().toString().trim();
                String ageStr = editAge.getText().toString().trim();

                if(name.isEmpty() || salaryStr.isEmpty() || ageStr.isEmpty()){
                    return;
                }

                int salary = Integer.parseInt(salaryStr);
                int age = Integer.parseInt(ageStr);

                Member member = new Member(name, salary, age);

                Intent intent = new Intent();
                intent.putExtra("member", member);
                setResult(22, intent);

                finish();

            }
        });

    }
}