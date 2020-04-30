package clubmanage.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import clubmanage.model.Club;
import clubmanage.model.User;
import clubmanage.ui.R;
import clubmanage.ui.adapter.ClubAdapter;
import clubmanage.util.ClubManageUtil;

public class Home_Club extends Fragment {
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
    private ClubAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    //这个构造方法是便于各导航同时调用一个fragment
    public Home_Club(String title){
        mTitle=title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.home_frament_tab2,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.home_refresh2);
        swipeRefreshLayout.setColorSchemeResources(R.color.titlecolorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initHomes();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initHomes();
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.home_body2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClubAdapter(clubList);
        recyclerView.setAdapter(adapter);
    }

    private void initHomes(){
        new Thread(){
            @Override
            public void run(){
                List<Club> clubList= null;
                clubList = ClubManageUtil.clubManage.searchMyClub(User.currentLoginUser.getUid());
                Message message=new Message();
                message.obj=clubList;
                handler.sendMessage(message);
            }
        }.start();
    }
}
