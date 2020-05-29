package clubmanage.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import clubmanage.httpInterface.AttentionRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Club;
import clubmanage.model.User;
import clubmanage.ui.adapter.ClubAdapter;
import clubmanage.util.ClubManageUtil;
import clubmanage.util.HttpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class My_attention extends AppCompatActivity {
    private List<Club> clubList = new ArrayList<>();
    private ClubAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Club> clubs=(List<Club>) msg.obj;
            clubList.clear();
            clubList.addAll(clubs);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_attention);
        Toolbar toolbar = findViewById(R.id.my_attention_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.my_attention_refresh);
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
        recyclerView = (RecyclerView)findViewById(R.id.recycler_my_attention);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClubAdapter(clubList);
        recyclerView.setAdapter(adapter);
    }

    private void initHomes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AttentionRequest request = retrofit.create(AttentionRequest.class);
        Call<HttpMessage<List<Club>>> call = request.searchAttenByUser(User.currentLoginUser.getUid());
        call.enqueue(new Callback<HttpMessage<List<Club>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Club>>> call, Response<HttpMessage<List<Club>>> response) {
                HttpMessage data=response.body();
                if (data.getCode()==200){
                    Message message=new Message();
                    message.obj=data.getData();
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Club>>> call, Throwable t) {
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
