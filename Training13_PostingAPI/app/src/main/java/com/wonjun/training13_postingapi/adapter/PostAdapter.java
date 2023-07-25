package com.wonjun.training13_postingapi.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wonjun.training13_postingapi.R;
import com.wonjun.training13_postingapi.api.NetworkClient;
import com.wonjun.training13_postingapi.api.PostApi;
import com.wonjun.training13_postingapi.config.Config;
import com.wonjun.training13_postingapi.model.Post;
import com.wonjun.training13_postingapi.model.ResultRes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    Context context;
    ArrayList<Post> postArrayList;

    SimpleDateFormat sf;
    SimpleDateFormat df;

    public PostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;

        // 한번만 실행 되도록 생성자에 넣자.
        sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df = new SimpleDateFormat("yyyy년MM월dd일 HH:mm");
        sf.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.setTimeZone(TimeZone.getDefault()); // 로컬을 들고옴
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postArrayList.get(position);

        holder.txtContent.setText(post.getContent());
        holder.txtEmail.setText(post.getEmail());

        // TODO: 2023-07-25  로컬타임 변경
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy년MM월dd일 HH:mm");
//        sf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        df.setTimeZone(TimeZone.getDefault()); // 로컬을 들고옴

        try {
            Date date = sf.parse(post.getCreatedAt()); // 자바가 이해할 수 있게 시간으로 변경
            String localTime = df.format(date);

            holder.txtCreatedAt.setText(localTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Glide.with(context)
                .load(post.getImgUrl())
                .placeholder(R.drawable.baseline_image_24)
                .into(holder.imgPost);

        if(post.getIsLike() == 1){
            holder.imgLike.setImageResource(R.drawable.baseline_thumb_up_alt_24);
        }
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtContent;
        TextView txtEmail;
        TextView txtCreatedAt;
        ImageView imgPost;
        ImageView imgLike;

        int index;
        Post post;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtContent = itemView.findViewById(R.id.txtContent);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            imgPost = itemView.findViewById(R.id.imgPost);
            imgLike = itemView.findViewById(R.id.imgLike);

            imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = getAdapterPosition();

                    post = postArrayList.get(index);

                    Retrofit retrofit = NetworkClient.getRetrofitClient(context);

                    PostApi api = retrofit.create(PostApi.class);
                    SharedPreferences sp = context.getSharedPreferences(Config.PREFERENCE, Context.MODE_PRIVATE);
                    String accessToken = sp.getString(Config.ACCESS_TOKEN, "");


                    if(post.getIsLike() == 0){
                        Call<ResultRes> call = api.setLikePost(accessToken, post.getPstingId());
                        call.enqueue(new Callback<ResultRes>() {
                            @Override
                            public void onResponse(Call<ResultRes> call, Response<ResultRes> response) {
                                if(response.isSuccessful()){
                                    imgLike.setImageResource(R.drawable.baseline_thumb_up_alt_24);

                                    post.setIsLike(1);
                                    //postArrayList.set(index, post);

                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultRes> call, Throwable t) {

                            }
                        });
                    }else {
                        Call<ResultRes> call = api.delListPost(accessToken, post.getPstingId());
                        call.enqueue(new Callback<ResultRes>() {
                            @Override
                            public void onResponse(Call<ResultRes> call, Response<ResultRes> response) {
                                if(response.isSuccessful()){
                                    imgLike.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);

                                    post.setIsLike(0);
                                    //postArrayList.set(index, post);

                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultRes> call, Throwable t) {

                            }
                        });
                    }
                }
            });

        }
    }
}
