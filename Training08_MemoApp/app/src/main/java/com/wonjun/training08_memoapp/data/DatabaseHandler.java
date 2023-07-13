package com.wonjun.training08_memoapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.wonjun.training08_memoapp.model.Memo;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //테이블 생성
        String query ="create table memo (" +
                " id integer primary key autoincrement," +
                " title text," +
                " context text" +
                ");";
        sqLiteDatabase.execSQL(query);
    }





    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //데이터 넣기
    public void insertMemo(Memo memo) {
        SQLiteDatabase db = getWritableDatabase();

        //쿼리문 작성
        String query = "insert into memo" +
                " (title, context)" +
                " values" +
                " (?, ?)";
        String [] record = {memo.getTitle(), memo.getContext()};


        //쿼리 실행
        db.execSQL(query, record);
        //사용 후 db 닫기
        db.close();
    }

    //데이터 전체 조회
    public ArrayList<Memo> getAllSelectMemo() {
        SQLiteDatabase db = getWritableDatabase();

        //쿼리문 작성
        String query = "select * " +
                " from memo ";

        //데이터 가져오기 위해서는 커서가 필요하다.
        Cursor cursor = db.rawQuery(query, null);

        //데이터를 담을 arraylist 작성
        ArrayList<Memo> memoArrayList = new ArrayList<>();

        if(cursor.moveToFirst()){ //가장 첫번째로 커서를 이동시키고 값이 잇다면,
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String context = cursor.getString(2);

                Memo memo = new Memo(id, title, context);

                memoArrayList.add(memo);

            }while(cursor.moveToNext()); //값을 다음으로 옮기고 값이 있다면?
        }

        cursor.close();
        db.close();

        return memoArrayList;
    }

    public void updateMemo(Memo memo) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "update memo" +
                " set title = ?, context = ?" +
                " where id = ?";
        String[] record = {memo.getTitle(), memo.getContext(), memo.getId() + ""};

        db.execSQL(query, record);

        db.close();
    }

    public void deleteMemo(Memo memo) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "delete from memo" +
                " where id = ?";
        String [] record = {memo.getId() +""};

        db.execSQL(query, record);

        db.close();
    }

    public ArrayList<Memo> getSelectMemo(String search) {
        SQLiteDatabase db = getWritableDatabase();

        //쿼리문 작성
        String query = "select * " +
                " from memo" +
                " where context like ?";
        String[] record = {"%"+search+"%"};

        //데이터 가져오기 위해서는 커서가 필요하다.
        Cursor cursor = db.rawQuery(query, record);

        //데이터를 담을 arraylist 작성
        ArrayList<Memo> memoArrayList = new ArrayList<>();

        if(cursor.moveToFirst()){ //가장 첫번째로 커서를 이동시키고 값이 잇다면,
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String context = cursor.getString(2);

                Memo memo = new Memo(id, title, context);

                memoArrayList.add(memo);

            }while(cursor.moveToNext()); //값을 다음으로 옮기고 값이 있다면?
        }

        cursor.close();
        db.close();

        return memoArrayList;
    }
}
