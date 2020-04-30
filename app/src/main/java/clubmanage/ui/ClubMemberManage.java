package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import clubmanage.model.Club;
import clubmanage.model.User;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;

public class ClubMemberManage extends AppCompatActivity {
    private int clubid;
    private boolean iscap;
    private List<User> userList=new ArrayList<>();
    private RecyclerView recyclerView;
    private ClubMemberAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<User> activityList=(List<User>) msg.obj;
            userList.clear();
            userList.addAll(activityList);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_member_manage);
        Intent intentget=getIntent();
        clubid=(int)intentget.getSerializableExtra("clubid");
        iscap=(boolean)intentget.getBooleanExtra("iscap",false);
        Toolbar toolbar = findViewById(R.id.club_member_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.member_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.titlecolorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initUsers();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initUsers();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_club_member);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClubMemberAdapter(userList,iscap,clubid);
        recyclerView.setAdapter(adapter);
    }

    public void initUsers(){
        new Thread(){
            @Override
            public void run() {
                List<User> userList=new ArrayList<>();
                try {
                    userList.addAll(ClubManageUtil.clubManage.searchMember(clubid));
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                message.obj=userList;
                handler.sendMessage(message);
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
