package com.wonjun.simplememo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.simplememo.adapter.MemoAdapter;
import com.wonjun.simplememo.model.Memo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editMemo;
    Button btnSave;

    //라사이클러뷰 는, 함께 사용하는 변수들이 있다.
        // MemoAdapter가 작성이 완료되면 이제 사용을 하기 시작한다.
    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList = new ArrayList<>(); //데이터를 담을 배열을 만든다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMemo = findViewById(R.id.editMemo);
        btnSave = findViewById(R.id.btnSave);


//리사이클 찾기
recyclerView = findViewById(R.id.recyclerView);
    // 초기화 작업
recyclerView.setHasFixedSize(true);
recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


//버튼 누를시 작동
btnSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String content = editMemo.getText().toString().trim();

        if(content.isEmpty()){
            Snackbar.make(btnSave,
                    "메모 필수!",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        // 메모 클래스 객채 생성 후 데이터를 저장한다.
        Memo memo = new Memo(content);

        // 메모가 여러개 이므로, 어레이리스트에 넣어준다.
        //memoList.add(memo);
        memoList.add(0, memo); //입력한 메모가 가장 최근의 위주로 보여주고 싶다.
            //불러올때 0부터 불러온다면, 애초에 0의 자리에 최근 데이터를 넣으면 되지 않나?

        //어뎁터에 만든 ArrayList와 Main액티비티를 넣는다.
        //adapter = new MemoAdapter(MainActivity.this, memoList);
        //recyclerView.setAdapter(adapter); //사이클에 적용

        editMemo.setText(""); //메모 작성 초기화

        // 코드를 최적화 할때 문제가 발생한다.
        // 데이터가 추가될때마다 업데이트가 안된다는것인데, 아래의 코드를 이용해 업데이트 하자.
        adapter.notifyDataSetChanged(); //데이터가 변경되었다고 알려줌.
    }
});


// 버튼 누를때 마다, 객체가 생성되면 메모리에 무한정 생기니
// 생성되는건 한번만 생성되도록 하자.
adapter = new MemoAdapter(MainActivity.this, memoList);
recyclerView.setAdapter(adapter);
    }
}