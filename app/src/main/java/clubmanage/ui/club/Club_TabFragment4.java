package clubmanage.ui.club;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import clubmanage.httpInterface.ClubRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Club;
import clubmanage.ui.R;
import clubmanage.ui.adapter.ClubAdapter;
import clubmanage.util.ClubManageUtil;
import clubmanage.util.HttpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Club_TabFragment4 extends Fragment {
    private List<Club> clubList = new ArrayList<>();
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Club> clubs=(List<Club>) msg.obj;
            clubList.clear();
            clubList.addAll(clubs);
            adapter.notifyDataSetChanged();
        }
    };
    private String mTitle;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    ClubAdapter adapter;
    //这个构造方法是便于各导航同时调用一个fragment
    public Club_TabFragment4(String title){
        mTitle=title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.club_frament_tab4,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.club_refresh4);
        swipeRefreshLayout.setColorSchemeResources(R.color.titlecolorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initActs();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initActs();
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.act_body4);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClubAdapter(clubList);
        recyclerView.setAdapter(adapter);
    }

    private void initActs(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClubRequest request = retrofit.create(ClubRequest.class);
        Call<HttpMessage<List<Club>>> call = request.searchClubByType(mTitle,false);
        call.enqueue(new Callback<HttpMessage<List<Club>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Club>>> call, Response<HttpMessage<List<Club>>> response) {
                HttpMessage<List<Club>> data=response.body();
                if (data.getCode()==200){
                    List<Club> clubList = (List<Club>)data.getData();
                    Message message=new Message();
                    message.obj=clubList;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Club>>> call, Throwable t) {
                Log.i("Club_TabFragment4",t.getMessage());
            }
        });
    }

}
