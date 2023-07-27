package com.wonjun.tabbarbottomtabbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wonjun.tabbarbottomtabbar.adapter.PostAdapter;
import com.wonjun.tabbarbottomtabbar.api.NetworkClient;
import com.wonjun.tabbarbottomtabbar.api.PostApi;
import com.wonjun.tabbarbottomtabbar.config.Config;
import com.wonjun.tabbarbottomtabbar.model.Post;
import com.wonjun.tabbarbottomtabbar.model.PostRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FristFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FristFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FristFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FristFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FristFragment newInstance(String param1, String param2) {
        FristFragment fragment = new FristFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // TODO: 2023-07-26 https://readystory.tistory.com/199 
    // TODO: 2023-07-26 Fragment LifeCycle 

    Button btnPostAdd;
    ProgressBar progressBar;

    String accessToken;

    // 프래그먼트에서 리사클러뷰 사용하기
    RecyclerView recyclerView;
    ArrayList<Post> postArrayList = new ArrayList<>();
    PostAdapter adapter;

    // 페이징 처리에 필요한 변수
    int offset = 0;
    int limit = 5;
    int count = 0;

    // 해당 함수가 보여주기 직전 함수
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_frist, container, false);
        // adapter의 viewholder와 비슷한 형태로 가져온다.


        // 부모의 액티비티를 불러와서 사용할 수 있다.
            // fragment class는 액티비티가 아니기에 사용할 수 없으나, 부모의 액티비티를 불러오면 가능하다.
        SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE, Activity.MODE_PRIVATE);
        accessToken = sp.getString(Config.ACCESS_TOKEN, "");
//        if(accessToken.isEmpty()){
//            Intent intent = new Intent(getActivity(), UserRegisterActivity.class);
//
//            startActivity(intent);
//            getActivity().finish();
//            return null;
//        } //해당 코드는 프레그먼트에서 사용되는 코드가 아니다. 액티비에서 사용되도록 하자.


        btnPostAdd = rootView.findViewById(R.id.btnPostAdd);
        progressBar = rootView.findViewById(R.id.progressBar);


        // 프래그먼트에서 리사이클러뷰 처리하기
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition(); //맨 마지막 위치
                int totalCount = recyclerView.getAdapter().getItemCount(); //arrayList값의 크기를 가져온다.

                if(lastPosition + 1 == totalCount){ //스크롤을 데이터 맨 끝까지 한 상태이므로
                    //네트워크를 통해서 데이터를 추가로 받아오면 된다.

                    if(count == limit){
                        getNetworkPost();
                    }

                }
            }
        });

        btnPostAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostCreateActivity.class);
                startActivity(intent);
            }
        });

//        getAllListPost();

        return rootView;
    }

    protected void getAllListPost(){
        postArrayList.clear();
        offset = 0;

        getNetworkPost();
    }
    protected void getNetworkPost(){
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());

        PostApi api = retrofit.create(PostApi.class);
        Call<PostRes> call = api.getAllPost(accessToken, offset, limit);
        call.enqueue(new Callback<PostRes>() {
            @Override
            public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if(response.isSuccessful()){
                    PostRes res = response.body();

                    // 페이징을 위해 count를 저장시킨
                    count = res.getCount();
                    offset = offset + count;

                    postArrayList.addAll(res.getItems());

                    if(adapter != null){
                        adapter.notifyDataSetChanged();
                    }else{
                        adapter = new PostAdapter(getActivity(), postArrayList);
                        recyclerView.setAdapter(adapter);
                    }

                }else {
                    Intent intent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(intent);

                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<PostRes> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onStart() {
        getAllListPost();
        super.onStart();
    }
}