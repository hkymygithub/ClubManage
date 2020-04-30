package clubmanage.ui.club;

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
import clubmanage.ui.R;
import clubmanage.ui.adapter.ClubAdapter;
import clubmanage.util.ClubManageUtil;

public class Club_TabFragment2 extends Fragment {
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
    public Club_TabFragment2(String title){
        mTitle=title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.club_frament_tab2,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.club_refresh2);
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
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.act_body2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClubAdapter(clubList);
        recyclerView.setAdapter(adapter);
    }

    private void initActs(){
//        for(int i=0;i<5;i++){
//            ClubItem one = new ClubItem(R.drawable.photo3,"体育社","热门社团","成员128","粉丝1273","动漫","颜值","cosplay","“动漫”这一合称的出现主要是因为日本的动画和漫画产业联系紧密，所以日本动画和漫画在中国传播的过程中，出现了《动漫时代》这样综合了日本动画和漫画咨询的杂志。");
//            actList.add(one);
//            ClubItem two = new ClubItem(R.drawable.photo4,"学习社","十佳社团","成员128","粉丝1273","读书","颜值","定期团建","“动漫”这一合称的出现主要是因为日本的动画和漫画产业联系紧密，所以日本动画和漫画在中国传播的过程中，出现了《动漫时代》这样综合了日本动画和漫画咨询的杂志。");
//            actList.add(two);
//        }
        new Thread(){
            @Override
            public void run() {
                List<Club> clubList= null;
                clubList = ClubManageUtil.clubManage.searchClubByType(mTitle,false);
                Message message=new Message();
                message.obj=clubList;
                handler.sendMessage(message);
//                Log.i("Club_TabFragment2","***************"+mTitle+"  1");
            }
        }.start();
    }

}
