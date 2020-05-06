package clubmanage.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import clubmanage.model.Create_activity;
import clubmanage.model.Create_club;
import clubmanage.util.ClubManageUtil;

public class CheckClub extends AppCompatActivity {
    private List<Create_club> CheckClubMsgList =new ArrayList<>();
    private RecyclerView recyclerView;
    private CheckClubAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Create_club> clubList=(List<Create_club>) msg.obj;
            CheckClubMsgList.clear();
            CheckClubMsgList.addAll(clubList);
            adapter.notifyDataSetChanged();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_club);
        Toolbar toolbar = findViewById(R.id.club_toolbar_check);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.club_check_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.titlecolorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initClub();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initClub();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_club_check);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CheckClubAdapter(CheckClubMsgList);
        recyclerView.setAdapter(adapter);
    }
    private void initClub(){
        new Thread(){
            @Override
            public void run() {
                List<Create_club> clubList= ClubManageUtil.applicationManage.searchCreateClubAppli();
                Message message=new Message();
                message.obj=clubList;
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
    @Override
    protected void onResume() {
        super.onResume();
        initClub();
    }
}
