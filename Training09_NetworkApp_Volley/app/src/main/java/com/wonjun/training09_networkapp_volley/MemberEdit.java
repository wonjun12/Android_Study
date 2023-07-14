package com.wonjun.training09_networkapp_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wonjun.training09_networkapp_volley.model.Member;

public class MemberEdit extends AppCompatActivity {

    EditText editSalary;
    EditText editAge;
    Button btnSave;

    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_edit);

        editSalary = findViewById(R.id.editSalary);
        editAge = findViewById(R.id.editAge);
        btnSave = findViewById(R.id.btnSave);

        member = (Member) getIntent().getSerializableExtra("member");

        editSalary.setText(member.getSalary() + "");
        editAge.setText(member.getAge() + "");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String salaryStr = editSalary.getText().toString().trim();
                String ageStr = editAge.getText().toString().trim();

                if(salaryStr.isEmpty() || ageStr.isEmpty()){
                    return;
                }

                int salary = Integer.parseInt(salaryStr);
                int age = Integer.parseInt(ageStr);

                member.setAge(age);
                member.setSalary(salary);

                Intent intent = new Intent();
                intent.putExtra("index", getIntent().getIntExtra("index", 0));
                intent.putExtra("mamber", member);

                setResult(23, intent);

                finish();
            }
        });
    }
}