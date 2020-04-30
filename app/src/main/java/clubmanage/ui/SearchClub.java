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

import clubmanage.model.Club;

import clubmanage.ui.adapter.ClubAdapter;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;

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
        new Thread(){
            @Override
            public void run() {
                List<Club> clubList= new ArrayList<Club>();
                try {
                    clubList.addAll(ClubManageUtil.clubManage.searchAllClub(clubname.getText().toString(),false));
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                message.obj=clubList;
                handler.sendMessage(message);
            }
        }.start();
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