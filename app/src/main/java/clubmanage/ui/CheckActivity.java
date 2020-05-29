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

import clubmanage.httpInterface.ApplicationRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Create_activity;
import clubmanage.ui.adapter.CheckActivityAdapter;
import clubmanage.util.HttpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckActivity extends AppCompatActivity {
    private List<Create_activity> checkMsgList =new ArrayList<>();
    private ProgressBar progressBar;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Create_activity> activityList=(List<Create_activity>) msg.obj;
            checkMsgList.clear();
            checkMsgList.addAll(activityList);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    };
    private RecyclerView recyclerView;
    private CheckActivityAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Toolbar toolbar = findViewById(R.id.activity_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar=findViewById(R.id.progressBar);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.act_check_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.titlecolorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initActivity();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initActivity();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_acticity);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CheckActivityAdapter(checkMsgList);
        recyclerView.setAdapter(adapter);
    }
    public void initActivity(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApplicationRequest request = retrofit.create(ApplicationRequest.class);
        Call<HttpMessage<List<Create_activity>>> call = request.searchCreateActivityAppli();
        call.enqueue(new Callback<HttpMessage<List<Create_activity>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Create_activity>>> call, Response<HttpMessage<List<Create_activity>>> response) {
                HttpMessage<List<Create_activity>> data=response.body();
                if (data.getCode()==200){
                    List<Create_activity> activityList = (List<Create_activity>)data.getData();
                    Message message=new Message();
                    message.obj=activityList;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Create_activity>>> call, Throwable t) {
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
    protected void onResume() {
        super.onResume();
        initActivity();
    }
}
