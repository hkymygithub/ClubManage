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

import java.util.ArrayList;
import java.util.List;

import clubmanage.httpInterface.ClubRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.User;
import clubmanage.ui.adapter.ClubMemberAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://121.36.153.113:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClubRequest request = retrofit.create(ClubRequest.class);
        Call<HttpMessage<List<User>>> call = request.searchMember(clubid);
        call.enqueue(new Callback<HttpMessage<List<User>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<User>>> call, Response<HttpMessage<List<User>>> response) {
                HttpMessage<List<User>> data=response.body();
                if (data.getCode()==0){
                    List<User> userList = (List<User>)data.getData();
                    Message message=new Message();
                    message.obj=userList;
                    handler.sendMessage(message);
                }else if (data.getCode()==1){

                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<User>>> call, Throwable t) {
            }
        });
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
