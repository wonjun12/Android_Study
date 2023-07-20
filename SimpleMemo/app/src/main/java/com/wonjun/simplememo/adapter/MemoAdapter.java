package com.wonjun.simplememo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonjun.simplememo.R;
import com.wonjun.simplememo.model.Memo;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {//2. 어댑터 클래스 상속받기
                                                            // 안에 작성할 클래스는 자기 자신의 뷰홀더를 작성한다.

    // 상속받은 후 에러 발생 (작성 해야할 부분이 있기때문이다)
    // 3. 오버라이딩 함수 작성


    // 4. 클래스의 멤버변수 작성한다. 기본 2개는 필수.
    Context context; //어떤 엑티비티에서 수행되어야 하는지 알아야해서 필요하다.
    ArrayList<Memo> memoList;//데이터를 저장할 공간이 필요하다.

    // 5. 위의 멤버 변수를 셋팅할 생성자를 만들자.
    public MemoAdapter(Context context, ArrayList<Memo> memoList) {
        this.context = context;
        this.memoList = memoList;
    }
    
    // 6. 오버라이딩 함수 전부 작성하자.

    @NonNull
    @Override //리턴 타입을 내가 만든 뷰홀더로 변경하자.(기존의 리턴타입과는 조금 다름)
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //memo_row.xml과 연결하는 코드
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_row, parent, false);

        return new MemoAdapter.ViewHolder(view); //memo_row와 연결한 view를 넘겼기때문에 뷰홀더에서 findView가 가능하다.
    }

    @Override //화면과 데이터를 매칭시켜서, 실제로 데이터를 화면에 적용시키는 함수
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memo memo = memoList.get(position);

        //자신이 만든 뷰홀더의 textView에 접근해서 텍스트를 변경하라.
        holder.txtContent.setText(memo.getContent());
    }

    @Override // 데이터의 갯수 함수 (행의 갯수)
    public int getItemCount() { //최대 보여줄 갯수
        return memoList.size();
    }




    // 1. 뷰홀더 클래스 만든다. (이너 클래스)
    // 해당 클래스에는 리스트에 들어갈 행화면 뷰들을 여기서 연결시킨다.
    public class ViewHolder extends RecyclerView.ViewHolder{

        // 행 화면에 있는 TextView를 작성
        TextView txtContent;
        
        //자동 완성 됨
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 여기서 연결시킨다.
                //findView가 상속받지 않아서 사용할 수 가 없다.
                // itemView에서 사용가능하다.
            txtContent = itemView.findViewById(R.id.txtContent); //R이 자동완성이 안된다.
                                                //class를 import 받아와서 작성해야한다.
                                            // Main에서 되는 이유는 setContentView(R.layout.activity_main);를 통해 main을 연결시켰기 때문이다.

        }
    }
}
