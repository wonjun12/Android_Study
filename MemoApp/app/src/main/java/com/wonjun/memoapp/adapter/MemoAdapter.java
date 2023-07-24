package com.wonjun.memoapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wonjun.memoapp.R;
import com.wonjun.memoapp.api.MemoApi;
import com.wonjun.memoapp.api.NetworkClient;
import com.wonjun.memoapp.config.Config;
import com.wonjun.memoapp.memo.EditActivity;
import com.wonjun.memoapp.model.Memo;
import com.wonjun.memoapp.model.MemoList;
import com.wonjun.memoapp.model.ResultRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        holder.txtContent.setText(memo.getContent());

        // TODO: 2023-07-21 날짜 형색 변경해야함
        String date = memo.getMemoDate();
        date = date.replace("T", " ").substring(0, date.length()-3);

        holder.txtMemoDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return memoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtContent;
        TextView txtMemoDate;
        ImageView imgDelete;

        CardView cardView;

        int index;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtContent = itemView.findViewById(R.id.txtContent);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtMemoDate = itemView.findViewById(R.id.txtMemoDate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = getAdapterPosition();

                    Memo memo = memoArrayList.get(index);
                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("memo", memo);

                    context.startActivity(intent);

                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertDialog();
                }
            });
        }

        private void showAlertDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context); //어디 엑티비티에서 보여줄것인지 지정해줘야함.
            builder.setTitle("삭제");
            builder.setMessage("정말 삭제하시겠습니까?");
            // 화면  바깥을 눌러도 사라진다.
            // 사라지는것을 막자.
            builder.setCancelable(false);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    index = getAdapterPosition();

                    Memo memo = memoArrayList.get(index);

                    SharedPreferences sp = context.getSharedPreferences(Config.PREFFERENCE_NAME, Context.MODE_PRIVATE);
                    String token = sp.getString(Config.ACCESS_TOKEN, "");

                    Retrofit retrofit = NetworkClient.getRetrofitClient(context);

                    MemoApi api = retrofit.create(MemoApi.class);
                    Call<ResultRes> cal = api.delMemo(memo.getId(), "Bearer " + token);
                    cal.enqueue(new Callback<ResultRes>() {
                        @Override
                        public void onResponse(Call<ResultRes> call, Response<ResultRes> response) {
                            if(response.isSuccessful()){
                                memoArrayList.remove(index);

                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultRes> call, Throwable t) {

                        }
                    });

                }
            });

            builder.setNegativeButton("NO", null);

            builder.show();
        }
    }
}
