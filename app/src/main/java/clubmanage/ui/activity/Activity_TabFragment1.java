package clubmanage.ui.activity;

import android.content.Intent;
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

import clubmanage.httpInterface.ActivityRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Activity;
import clubmanage.ui.R;
import clubmanage.ui.adapter.ActivityAdapter;
import clubmanage.util.ClubManageUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_TabFragment1 extends Fragment {
    private List<Activity> homeList = new ArrayList<>();
    private String mTitle;
    private RecyclerView recyclerView;
    private ActivityAdapter adapter;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Activity> activityList=(List<Activity>) msg.obj;
            homeList.clear();
            homeList.addAll(activityList);
            adapter.notifyDataSetChanged();
        }
    };
    private SwipeRefreshLayout swipeRefreshLayout;

    //这个构造方法是便于各导航同时调用一个fragment
    public Activity_TabFragment1(String title){
        mTitle=title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activity_frament_tab1,container,false);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initHomes();
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.activity_body1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ActivityAdapter(homeList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.activity_refresh1);
        swipeRefreshLayout.setColorSchemeResources(R.color.titlecolorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initHomes();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initHomes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://121.36.153.113:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ActivityRequest request = retrofit.create(ActivityRequest.class);
        Call<HttpMessage<List<Activity>>> call = request.searchAllActivity();
        call.enqueue(new Callback<HttpMessage<List<Activity>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Activity>>> call, Response<HttpMessage<List<Activity>>> response) {
                HttpMessage<List<Activity>> data=response.body();
                if (data.getCode()==0){
                    List<Activity> activityList = (List<Activity>)data.getData();
                    Message message=new Message();
                    message.obj=activityList;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Activity>>> call, Throwable t) {
                Log.i("Activity_TabFragment1",t.getMessage());
            }
        });
    }

}
