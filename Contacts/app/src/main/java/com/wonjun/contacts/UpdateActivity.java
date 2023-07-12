package com.wonjun.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wonjun.contacts.data.DatabaseHandler;
import com.wonjun.contacts.model.Contact;

public class UpdateActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

                //시리얼라이즈를 받아오면 형변환을 해서 사용해야한다.
        Contact contact = (Contact) getIntent().getSerializableExtra("contact");

        int id = contact.getId();

        editName.setText(contact.getName());
        editPhone.setText(contact.getPhone());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                DatabaseHandler handler = new DatabaseHandler(UpdateActivity.this, "contact_db", null, 1);
                handler.updateContact(id, name, phone);

                finish();
            }
        });

    }
}