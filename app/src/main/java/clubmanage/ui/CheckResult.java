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

import clubmanage.httpInterface.ApplicationRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Create;
import clubmanage.model.User;
import clubmanage.ui.adapter.CheckResultAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckResult extends AppCompatActivity {
    private List<Create> createList=new ArrayList<>();
    private List<CheckResultMsg> checkResultMsgs =new ArrayList<>();
    private RecyclerView recyclerView;
    private CheckResultAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private int f=0;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            checkResultMsgs.clear();
            createList=(List<Create>) msg.obj;
            for(int i=0;i<createList.size();i++){
                String a = null;
                if(createList.get(i).getState()==0)
                    a="申请驳回";
                else if(createList.get(i).getState()==1)
                    a="审核通过";
                else if(createList.get(i).getState()==2)
                    a="等待审核";
                byte[] bt=null;
                if (createList.get(i).getPoster()!=null) bt=Base64.decode(createList.get(i).getPoster(),Base64.DEFAULT);
                CheckResultMsg act=new CheckResultMsg(createList.get(i).getName(),bt,createList.get(i).getReason(),a);
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
                initData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initData();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_checkresult);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CheckResultAdapter(checkResultMsgs);
        recyclerView.setAdapter(adapter);
    }

    public void initData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://121.36.153.113:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApplicationRequest request = retrofit.create(ApplicationRequest.class);
        Call<HttpMessage<List<Create>>> call = request.searchCreateAppli(User.currentLoginUser.getUid());
        call.enqueue(new Callback<HttpMessage<List<Create>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Create>>> call, Response<HttpMessage<List<Create>>> response) {
                HttpMessage<List<Create>> data=response.body();
                if (data.getCode()==0){
                    List<Create> createdata = (List<Create>)data.getData();
                    Message message=new Message();
                    message.obj=createdata;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Create>>> call, Throwable t) {
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
