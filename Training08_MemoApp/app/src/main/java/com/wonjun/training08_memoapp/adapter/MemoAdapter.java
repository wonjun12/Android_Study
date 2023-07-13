package com.wonjun.training08_memoapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.wonjun.training08_memoapp.MemoAdd;
import com.wonjun.training08_memoapp.MemoEdit;
import com.wonjun.training08_memoapp.R;
import com.wonjun.training08_memoapp.data.DatabaseHandler;
import com.wonjun.training08_memoapp.model.Memo;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>{

    Context context;
    ArrayList<Memo> memoArrayList;

    public MemoAdapter(Context context, ArrayList<Memo> memoArrayList) {
        this.context = context;
        this.memoArrayList = memoArrayList;
    }

    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_row, parent, false);
        return new MemoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memo memo = memoArrayList.get(position);

        holder.txtTitle.setText(memo.getTitle());
        holder.txtContext.setText(memo.getContext());
    }

    @Override
    public int getItemCount() {
        return memoArrayList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtContext;
        ImageView imgDelete;

        LinearLayout editDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContext = itemView.findViewById(R.id.txtContext);
            imgDelete = itemView.findViewById(R.id.imgDelete);


            editDataLayout = itemView.findViewById(R.id.editDataLayout);

            // TODO: 2023-07-13 memo row 삭제 이미지 버튼 작성
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertDialog();
                }
            });

            // TODO: 2023-07-13 memo 데이터 수정 클릭
            editDataLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    Memo memo = memoArrayList.get(index);

                    Intent intent = new Intent(context, MemoEdit.class);
                    intent.putExtra("memo", memo);

                    context.startActivity(intent);
                }
            });
        }

        private void showAlertDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("메모 삭제");
            builder.setMessage("정말 삭제?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = getAdapterPosition();

                    Memo memo = memoArrayList.get(index);
                    //db 연결
                    DatabaseHandler handler = new DatabaseHandler(context, "memo_db", null, 1);
                    handler.deleteMemo(memo);

                    memoArrayList.remove(index);
                    notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("No", null);

            builder.show();
        }
    }

}
