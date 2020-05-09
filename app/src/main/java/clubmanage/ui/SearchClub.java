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

import clubmanage.httpInterface.ClubRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Club;

import clubmanage.ui.adapter.ClubAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchClub extends AppCompatActivity implements View.OnClickListener{
    private List<Club> searchClublist = new ArrayList<>();
    private ProgressBar progressBar;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Club> clubList=(List<Club>) msg.obj;
            searchClublist.clear();
            searchClublist.addAll(clubList);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    };
    private RecyclerView recyclerView;
    ClubAdapter adapter;
    EditText clubname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageView back=findViewById(R.id.img_array);
        back.setOnClickListener(this);
        Button search=findViewById(R.id.search_head_btn1);
        search.setOnClickListener(this);
        clubname=findViewById(R.id.home_head_edit1);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_searchClub);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClubAdapter(searchClublist);
        recyclerView.setAdapter(adapter);
    }

    private void initSearchClub() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://121.36.153.113:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClubRequest request = retrofit.create(ClubRequest.class);
        Call<HttpMessage<List<Club>>> call = request.searchAllClub(clubname.getText().toString(),false);
        call.enqueue(new Callback<HttpMessage<List<Club>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Club>>> call, Response<HttpMessage<List<Club>>> response) {
                HttpMessage<List<Club>> data=response.body();
                if (data.getCode()==0){
                    List<Club> clubs = (List<Club>)data.getData();
                    Message message=new Message();
                    message.obj=clubs;
                    handler.sendMessage(message);
                }else if (data.getCode()==1){

                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Club>>> call, Throwable t) {
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
                initSearchClub();
                adapter.notifyDataSetChanged();
                break;
        }
    }
}