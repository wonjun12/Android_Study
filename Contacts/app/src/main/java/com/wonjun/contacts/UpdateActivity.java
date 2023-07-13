package com.wonjun.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.contacts.data.DatabaseHandler;
import com.wonjun.contacts.model.Contact;

public class UpdateActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    Button btnSave;

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

                //시리얼라이즈를 받아오면 형변환을 해서 사용해야한다.
        contact = (Contact) getIntent().getSerializableExtra("contact");

        editName.setText(contact.getName());
        editPhone.setText(contact.getPhone());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Snackbar.make(
                            btnSave,
                            "필수 항목 입력하세요.",
                            Snackbar.LENGTH_SHORT
                    ).show();

                    return;
                }

                contact.setName(name);
                contact.setPhone(phone);

                DatabaseHandler handler = new DatabaseHandler(UpdateActivity.this, "contact_db", null, 1);
                handler.updateContact(contact);

                Toast.makeText(UpdateActivity.this, "수정 완료!!", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }
}