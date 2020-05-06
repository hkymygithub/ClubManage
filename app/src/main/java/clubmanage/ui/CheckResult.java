package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import clubmanage.model.Create_activity;
import clubmanage.model.Create_club;
import clubmanage.model.User;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;

public class CheckResult extends AppCompatActivity {
    private List<Create_activity> createActivitiesrList=new ArrayList<>();
    private List<Create_club> createClubList=new ArrayList<>();
    private List<CheckResultMsg> checkResultMsgs =new ArrayList<>();
    private RecyclerView recyclerView;
    private CheckResultAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private int f=0;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Create_activity> cractivityList=(List<Create_activity>) msg.obj;
            createActivitiesrList.clear();
            createActivitiesrList.addAll(cractivityList);

            for(int i=0;i<createActivitiesrList.size();i++){
                String a = null;
                if(createActivitiesrList.get(i).getState()==0)
                    a="申请驳回";
                else if(createActivitiesrList.get(i).getState()==1)
                    a="审核通过";
                else if(createActivitiesrList.get(i).getState()==2)
                    a="等待审核";
                CheckResultMsg act=new CheckResultMsg(createActivitiesrList.get(i).getActivity_name(), Base64.decode(createActivitiesrList.get(i).getPoster(),Base64.DEFAULT),createActivitiesrList.get(i).getSuggestion(),a);
                checkResultMsgs.add(act);
            }
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    };
    private Handler handler2=new Handler(){
        public void handleMessage(Message msg){
            List<Create_club> clubList=(List<Create_club>) msg.obj;
            createClubList.clear();
            createClubList.addAll(clubList);
            checkResultMsgs.clear();
            for(int i=0;i<createClubList.size();i++){
                String a = null;
                if(createClubList.get(i).getState()==0)
                    a="申请驳回";
                else if(createClubList.get(i).getState()==1)
                    a="审核通过";
                else if(createClubList.get(i).getState()==2)
                    a="等待审核";
                CheckResultMsg act=new CheckResultMsg(createClubList.get(i).getClub_name(),null,createClubList.get(i).getSuggestion(),a);
                checkResultMsgs.add(act);
            }
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_result);
        Toolbar toolbar = findViewById(R.id.checkResult_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.checkresult_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.titlecolorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initActivity();
                initClub();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initActivity();
        initClub();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_checkresult);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CheckResultAdapter(checkResultMsgs);
        recyclerView.setAdapter(adapter);
    }
    private void initActivity(){
        new Thread(){
            @Override
            public void run() {
                List<Create_activity> createActivitiesrList=new ArrayList<>();
                createActivitiesrList.addAll(ClubManageUtil.applicationManage.searchCreateActivityByUser(User.currentLoginUser.getUid()));
                Message message=new Message();
                message.obj=createActivitiesrList;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void initClub(){
        new Thread(){
            @Override
            public void run() {
                List<Create_club> createClubList=new ArrayList<>();
                createClubList.addAll(ClubManageUtil.applicationManage.searchCreateClubByUser(User.currentLoginUser.getUid()));
                Message message=new Message();
                message.obj=createClubList;
                handler2.sendMessage(message);
            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
