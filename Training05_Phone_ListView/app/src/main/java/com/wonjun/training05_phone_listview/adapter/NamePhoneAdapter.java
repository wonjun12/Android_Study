package com.wonjun.training05_phone_listview.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.wonjun.training05_phone_listview.R;
import com.wonjun.training05_phone_listview.model.NamePhone;

import java.util.ArrayList;

public class NamePhoneAdapter extends RecyclerView.Adapter<NamePhoneAdapter.ViewHolder>{

    //수행 액티비티 변수 작성
    Context context;
    //데이터 저장 공간
    ArrayList<NamePhone> namePhoneList;

    //초기화 생성자 생성
    public NamePhoneAdapter(Context context, ArrayList<NamePhone> namePhoneList) {
        this.context = context;
        this.namePhoneList = namePhoneList;
    }

    @NonNull
    @Override
    public NamePhoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //phone_row.xml과 연결
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_row, parent, false);

        return new NamePhoneAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //화면에 데이터 보여줌.
        NamePhone namePhone = namePhoneList.get(position);

        //텍스트 출력
        holder.txtName.setText(namePhone.getName());
        holder.txtPhone.setText(namePhone.getPhone());
    }

    @Override
    public int getItemCount() {
        return namePhoneList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
            TextView txtName;
            TextView txtPhone;
            ImageButton btnDelete;


            int selected;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                //리스트 갯수당 연결하는 함수
                txtName = itemView.findViewById(R.id.txtName);
                txtPhone = itemView.findViewById(R.id.txtPhone);

                btnDelete = itemView.findViewById(R.id.btnDelete);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //삭제하려는 주소록 번호 저장
                        selected = getAdapterPosition();
                        //다이아로그 show
                        showAlertDialog();


                    }
                });
            }

            private void showAlertDialog(){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("주소록 삭제");
                builder.setMessage("정말 삭제하시겠습니까?");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 해당 주소록 삭제
                        namePhoneList.remove (selected);
                        NamePhoneAdapter.super.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        }
}
