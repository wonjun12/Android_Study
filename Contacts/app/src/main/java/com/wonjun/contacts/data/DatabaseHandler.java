package com.wonjun.contacts.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.wonjun.contacts.model.Contact;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //테이블 생성문 작성

        String query = "create table contact (  " +
                "id integer primary key autoincrement," +
                "name text," +
                "phone text" +
                " );";

        sqLiteDatabase.execSQL(query);

    }

    //필요한 CRUD 관련 메서드 만들어야한다.
        //직접 만들자
    public void addContact(Contact contact){ // Create 데이터 넣기 함수
        //데이터 베이스를 가져오는 함수
        SQLiteDatabase db = getWritableDatabase();

        //쿼리문 작성
        String query = "insert into contact" +
                "(name, phone)" +
                "values" +
                "(?, ?);";
        String [] record = {contact.getName(), contact.getPhone()};

        //db 실행
        db.execSQL(query, record);

        db.close();
    }
    
    // Read 저장된 연락처 가져오는 메서드
    public ArrayList<Contact> getAllContacts(){
        SQLiteDatabase db = getWritableDatabase();

        String query = "select * from contact";

        //select는 무조건 커서가 있어야한다.
        Cursor cursor = db.rawQuery(query, null);

        //담을 arraylist 만들자.
        ArrayList<Contact> contactArrayList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                //값을 넣고 가져오다보니 ID 변수가 필요하다.
                    // model 클래스에 id를 만들자.
                int id = cursor.getInt(0); //첫번째 컬럼의 값
                String name = cursor.getString(1); //두번째 컬럼의 값
                String phone = cursor.getString(2);//세번째 컬럼의 값

                Contact contact = new Contact(id, name, phone);

                contactArrayList.add(contact);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return contactArrayList;
    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        String query = "delete from contact " +
                "where id = ?";

        String[] record = {contact.getId()+""};

        db.execSQL(query, record);

        db.close();
    }

    //업데이트 sql
    public void updateContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        String query = "update contact " +
                "set name = ?, phone = ? " +
                "where id = ?";
        String [] record = {contact.getName(), contact.getPhone(), contact.getId()+""};

        db.execSQL(query, record);

        db.close();
    }
    

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //기존 테이블 삭제하고, 새 테이블 생성시 코드 작성
    }
}
