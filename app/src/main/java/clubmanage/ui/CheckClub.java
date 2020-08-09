package clubmanage.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import clubmanage.httpInterface.ApplicationRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Create_club;
import clubmanage.ui.adapter.CheckClubAdapter;
import clubmanage.util.HttpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
//        initClub();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_club_check);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CheckClubAdapter(CheckClubMsgList);
        recyclerView.setAdapter(adapter);
    }
    private void initClub(){
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApplicationRequest request = retrofit.create(ApplicationRequest.class);
        Call<HttpMessage<List<Create_club>>> call = request.searchCreateClubAppli();
        call.enqueue(new Callback<HttpMessage<List<Create_club>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Create_club>>> call, Response<HttpMessage<List<Create_club>>> response) {
                HttpMessage<List<Create_club>> data=response.body();
                if (data.getCode()==200){
                    List<Create_club> clubList = (List<Create_club>)data.getData();
                    Message message=new Message();
                    message.obj=clubList;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Create_club>>> call, Throwable t) {
               Log.i("CheckClub","错了");
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

    @Override
    protected void onStart() {
        super.onStart();
        initClub();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initClub();
    }
}
