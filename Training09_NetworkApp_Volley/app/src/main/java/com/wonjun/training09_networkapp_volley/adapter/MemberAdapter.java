package com.wonjun.training09_networkapp_volley.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonjun.training09_networkapp_volley.MainActivity;
import com.wonjun.training09_networkapp_volley.MemberEdit;
import com.wonjun.training09_networkapp_volley.R;
import com.wonjun.training09_networkapp_volley.model.Member;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MemberAdapter extends  RecyclerView.Adapter<MemberAdapter.ViewHolder>{

    Context context;
    ArrayList<Member> memberArrayList;

    public MemberAdapter(Context context, ArrayList<Member> memberArrayList) {
        this.context = context;
        this.memberArrayList = memberArrayList;
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_row, parent, false);
        return new MemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member member = memberArrayList.get(position);

        holder.txtName.setText(member.getName());
        holder.txtAge.setText("나이 : " +member.getAge() + "세");

        // TODO: 2023-07-14  숫자 돈처럼 쉼표 추가하기
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        holder.txtSalary.setText("연봉 : $" + decimalFormat.format(member.getSalary()));
    }

    @Override
    public int getItemCount() {
        return memberArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtAge;
        TextView txtSalary;

        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();

                    memberArrayList.remove(index);
                    notifyDataSetChanged();
                }
            });

            // TODO: 2023-07-14 수정 버튼
            txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MemberEdit.class);

                    int index = getAdapterPosition();

                    Member member = memberArrayList.get(index);

                    intent.putExtra("index", index);
                    intent.putExtra("member", member);

                    //context.startActivity(intent);
                    // context자체는 Mainactivity인지 모른다.
                    // 지정을 함으로써 사용할 수 있게 변환을 해주고, launcher를 실행시킨다.
                    ((MainActivity) context).launcher.launch(intent);
                }
            });
        }
    }
}
