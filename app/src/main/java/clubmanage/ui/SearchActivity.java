package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import clubmanage.httpInterface.ActivityRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Activity;
import clubmanage.ui.adapter.ActivityAdapter;
import clubmanage.util.ClubManageUtil;
import clubmanage.util.HttpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private List<Activity> SearchActivitylist = new ArrayList<>();
    private RecyclerView recyclerView;
    private ActivityAdapter adapter;
    private EditText searchCon;
    private ProgressBar progressBar;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Activity> activityList=(List<Activity>) msg.obj;
            SearchActivitylist.clear();
            SearchActivitylist.addAll(activityList);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageView back=findViewById(R.id.img_array);
        back.setOnClickListener(this);
        Button search=findViewById(R.id.search_head_btn1);
        search.setOnClickListener(this);
        searchCon=(EditText)findViewById(R.id.home_head_edit1);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_searchClub);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ActivityAdapter(SearchActivitylist);
        recyclerView.setAdapter(adapter);
    }

    private void initSearchActivity() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ActivityRequest request = retrofit.create(ActivityRequest.class);
        Call<HttpMessage<List<Activity>>> call = request.searchActivityByName(searchCon.getText().toString());
        call.enqueue(new Callback<HttpMessage<List<Activity>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Activity>>> call, Response<HttpMessage<List<Activity>>> response) {
                HttpMessage<List<Activity>> data=response.body();
                if (data.getCode()==200){
                    List<Activity> activityList = (List<Activity>)data.getData();
                    Message message=new Message();
                    message.obj=activityList;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Activity>>> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_array:
                finish();
                break;
            case R.id.search_head_btn1:
                progressBar.setVisibility(View.VISIBLE);
                initSearchActivity();
                adapter.notifyDataSetChanged();
                break;
        }
    }
}