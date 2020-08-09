package clubmanage.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import clubmanage.Tools.BitmapAndBytes;
import clubmanage.httpInterface.ActivityRequest;
import clubmanage.httpInterface.AttentionRequest;
import clubmanage.httpInterface.ClubRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Activity;
import clubmanage.model.Club;
import clubmanage.model.User;
import clubmanage.ui.adapter.ActivityAdapter;
import clubmanage.ui.adapter.TopAdapter;
import clubmanage.util.HttpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClubGroup extends AppCompatActivity implements View.OnClickListener {
    private List<User> topList = new ArrayList<>();
    private List<Activity> actList = new ArrayList<>();
    private int clubid;
    private Club clubMsg;
    private RecyclerView recyclerView2;
    private TopAdapter adapter2;
    private RecyclerView recyclerView3;
    private ActivityAdapter adapter3= new ActivityAdapter(actList);
    private String notice;

    private RelativeLayout join;
    private RelativeLayout gonggao;
    private RelativeLayout rel_join;
    private TextView gonggao_text;
    private TextView name;
    private TextView memberNum;
    private TextView introduce;
    private TextView slogan;
    private CircleImageView logo;
    private RelativeLayout poster;

    private Button btatt;
    private ImageButton member;
    private boolean atted=false;
    private boolean joined=false;
    private boolean iscap=false;

    private Handler handler1=new Handler(){
        public void handleMessage(Message msg){
            atted=(boolean)msg.obj;
            if (atted==false){
                btatt.setBackgroundResource(R.drawable.button_shape_clubbutton);
                btatt.setText("关注");
            }else {
                btatt.setBackgroundResource(R.drawable.button_shape_clubbutton2);
                btatt.setText("已关注");
            }
        }
    };
    private Handler handler2=new Handler(){
        public void handleMessage(Message msg){
            List<User> users=(List<User>) msg.obj;
            topList.clear();
            topList.addAll(users);
            adapter2.notifyDataSetChanged();
        }
    };
    private Handler handler3=new Handler(){
        public void handleMessage(Message msg){
            List<Activity> activitys=(List<Activity>) msg.obj;
            actList.clear();
            actList.addAll(activitys);
            if (joined!=true){
                if (actList.isEmpty()==false){
                    for (int i=0;i<actList.size();i++){
                        if (actList.get(i).getIf_public_activity()==0){
                            actList.remove(i);
                            i--;
                        }
                    }
                }
            }
            adapter3.notifyDataSetChanged();
        }
    };
    private Handler handler4=new Handler(){
        public void handleMessage(Message msg){
            notice=(String) msg.obj;
            if (notice==null) gonggao_text.setText("暂无公告");
            else gonggao_text.setText(notice);
        }
    };
    private Handler handler5=new Handler(){
        public void handleMessage(Message msg){
            joined=(boolean)msg.obj;
            initActs();
            if (joined==true) {
                rel_join.setVisibility(View.GONE);
                gonggao.setVisibility(View.VISIBLE);
            }else {
                rel_join.setVisibility(View.VISIBLE);
                gonggao.setVisibility(View.GONE);
            }
        }
    };
    private Handler handler6=new Handler(){
        public void handleMessage(Message msg){
            iscap=(boolean)msg.obj;
            Intent intent=new Intent(ClubGroup.this,ClubMemberManage.class);
            intent.putExtra("clubid",clubid);
            intent.putExtra("iscap",iscap);
            startActivity(intent);
        }
    };
    private Handler handler7=new Handler(){
        public void handleMessage(Message msg){
            clubMsg=(Club) msg.obj;
            if (clubMsg.getClub_icon()==null) logo.setImageResource(R.drawable.photo1);
            else logo.setImageBitmap(BitmapAndBytes.bytesToBitmap(Base64.decode(clubMsg.getClub_icon(),Base64.DEFAULT)));
            if (clubMsg.getClub_cover()==null) poster.setBackgroundResource(R.drawable.poster);
            else poster.setBackground(BitmapAndBytes.bytesToDrawable(Base64.decode(clubMsg.getClub_cover(),Base64.DEFAULT)));
            name.setText(clubMsg.getClub_name());
            memberNum.setText("成员"+clubMsg.getMember_number());
            introduce.setText(clubMsg.getClub_introduce());
//            slogan.setSelected(true);
            slogan.setText(clubMsg.getSlogan());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_group);
        Intent intentget=getIntent();
        clubid=(int)intentget.getSerializableExtra("clubid");

        issubscribe();
        if_userInClub();

        logo=findViewById(R.id.club_item_img1);
        poster=findViewById(R.id.club1_head);
        name=findViewById(R.id.club_item_text1);
        memberNum=findViewById(R.id.club_item_text3);
        introduce=findViewById(R.id.club_item_text8);
        slogan=findViewById(R.id.club1_body1_text1);

        btatt=findViewById(R.id.club1_head_btn2);
        btatt.setOnClickListener(this);
        ImageButton back=(ImageButton)findViewById(R.id.club1_head_btn1);
        back.setOnClickListener(this);
        member=(ImageButton)findViewById(R.id.club1_body2_btn1);
        member.setOnClickListener(this);
        join=(RelativeLayout)findViewById(R.id.club_join);
        join.setOnClickListener(this);
        gonggao=(RelativeLayout)findViewById(R.id.club1_body4);
        gonggao.setVisibility(View.GONE);
        rel_join=(RelativeLayout)findViewById(R.id.rel_join);
        rel_join.setVisibility(View.GONE);
        gonggao_text=(TextView)findViewById(R.id.gonggao_text);

        recyclerView2= (RecyclerView)findViewById(R.id.club1_recycler2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new TopAdapter(topList);
        recyclerView2.setAdapter(adapter2);

        recyclerView3= (RecyclerView)findViewById(R.id.club1_recycler3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        recyclerView3.setLayoutManager(layoutManager3);
        adapter3 = new ActivityAdapter(actList);
        recyclerView3.setAdapter(adapter3);

        getClubMsg();
        initTops();
        initGonggao();

    }

    private void issubscribe(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AttentionRequest request = retrofit.create(AttentionRequest.class);
        Call<HttpMessage<Boolean>> call = request.issubscribe(User.currentLoginUser.getUid(),clubid);
        call.enqueue(new Callback<HttpMessage<Boolean>>() {
            @Override
            public void onResponse(Call<HttpMessage<Boolean>> call, Response<HttpMessage<Boolean>> response) {
                HttpMessage<Boolean> data=response.body();
                if (data.getCode()==200){
                    Boolean ifatt = (Boolean)data.getData();
                    Message message=new Message();
                    message.obj=ifatt;
                    handler1.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<Boolean>> call, Throwable t) {
            }
        });
    }

    private void if_userInClub(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClubRequest request = retrofit.create(ClubRequest.class);
        Call<HttpMessage<Boolean>> call = request.if_userInClub(User.currentLoginUser.getUid(),clubid);
        call.enqueue(new Callback<HttpMessage<Boolean>>() {
            @Override
            public void onResponse(Call<HttpMessage<Boolean>> call, Response<HttpMessage<Boolean>> response) {
                HttpMessage<Boolean> data=response.body();
                if (data.getCode()==200){
                    Boolean ifinclub = (Boolean)data.getData();
                    Message message=new Message();
                    message.obj=ifinclub;
                    handler5.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<Boolean>> call, Throwable t) {
            }
        });
    }

    private void initTops(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClubRequest request = retrofit.create(ClubRequest.class);
        Call<HttpMessage<List<User>>> call = request.searchMember(clubid);
        call.enqueue(new Callback<HttpMessage<List<User>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<User>>> call, Response<HttpMessage<List<User>>> response) {
                HttpMessage<List<User>> data=response.body();
                if (data.getCode()==200){
                    List<User> ifinclub = (List<User>)data.getData();
                    Message message=new Message();
                    message.obj=ifinclub;
                    handler2.sendMessage(message);
                }else if(data.getCode()==1){
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<User>>> call, Throwable t) {
            }
        });
    }

    private void initGonggao(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClubRequest request = retrofit.create(ClubRequest.class);
        Call<HttpMessage<String>> call = request.searchNotice(clubid);
        call.enqueue(new Callback<HttpMessage<String>>() {
            @Override
            public void onResponse(Call<HttpMessage<String>> call, Response<HttpMessage<String>> response) {
                HttpMessage<String> data=response.body();
                if (data.getCode()==200){
                    String notice = (String)data.getData();
                    Message message=new Message();
                    message.obj=notice;
                    handler4.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<String>> call, Throwable t) {
            }
        });
    }

    private void initActs(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ActivityRequest request = retrofit.create(ActivityRequest.class);
        Call<HttpMessage<List<Activity>>> call = request.searchActivityByClubId(clubid);
        call.enqueue(new Callback<HttpMessage<List<Activity>>>() {
            @Override
            public void onResponse(Call<HttpMessage<List<Activity>>> call, Response<HttpMessage<List<Activity>>> response) {
                HttpMessage<List<Activity>> data=response.body();
                if (data.getCode()==200){
                    List<Activity> activityList = (List<Activity>)data.getData();
                    Message message=new Message();
                    message.obj=activityList;
                    handler3.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<List<Activity>>> call, Throwable t) {
            }
        });
    }

    private void getClubMsg(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClubRequest request = retrofit.create(ClubRequest.class);
        Call<HttpMessage<Club>> call = request.searchClubByClubid(clubid);
        call.enqueue(new Callback<HttpMessage<Club>>() {
            @Override
            public void onResponse(Call<HttpMessage<Club>> call, Response<HttpMessage<Club>> response) {
                HttpMessage<Club> data=response.body();
                if (data.getCode()==200){
                    Club club = (Club)data.getData();
                    Message message=new Message();
                    message.obj=club;
                    handler7.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<Club>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.club1_head_btn2:
                if (atted==false){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(HttpUtil.httpUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    AttentionRequest request = retrofit.create(AttentionRequest.class);
                    Call<HttpMessage> call = request.addAttention(User.currentLoginUser.getUid(),clubid);
                    call.enqueue(new Callback<HttpMessage>() {
                        @Override
                        public void onResponse(Call<HttpMessage> call, Response<HttpMessage> response) {
                            HttpMessage data=response.body();
                            if (data.getCode()==200){
                            }
                        }
                        @Override
                        public void onFailure(Call<HttpMessage> call, Throwable t) {
                        }
                    });
                    Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
                    btatt.setBackgroundResource(R.drawable.button_shape_clubbutton2);
                    btatt.setText("已关注");
                    atted=true;
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(HttpUtil.httpUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    AttentionRequest request = retrofit.create(AttentionRequest.class);
                    Call<HttpMessage> call = request.deleteAttention(User.currentLoginUser.getUid(),clubid);
                    call.enqueue(new Callback<HttpMessage>() {
                        @Override
                        public void onResponse(Call<HttpMessage> call, Response<HttpMessage> response) {
                            HttpMessage data=response.body();
                            if (data.getCode()==200){
                            }
                        }
                        @Override
                        public void onFailure(Call<HttpMessage> call, Throwable t) {
                        }
                    });
                    Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
                    btatt.setBackgroundResource(R.drawable.button_shape_clubbutton);
                    btatt.setText("关注");
                    atted=false;
                }
                break;
            case R.id.club1_head_btn1:
                finish();
                break;
            case R.id.club1_body2_btn1:
                if (joined==false){
                    Toast.makeText(ClubGroup.this, "你不是本社成员，无法查看" , Toast.LENGTH_SHORT).show();
                    break;
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(HttpUtil.httpUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ClubRequest request = retrofit.create(ClubRequest.class);
                Call<HttpMessage<Boolean>> call = request.if_userIsCaptain(User.currentLoginUser.getUid(),clubid);
                call.enqueue(new Callback<HttpMessage<Boolean>>() {
                    @Override
                    public void onResponse(Call<HttpMessage<Boolean>> call, Response<HttpMessage<Boolean>> response) {
                        HttpMessage<Boolean> data=response.body();
                        if (data.getCode()==200){
                            boolean ifcap =(boolean) data.getData();
                            Message message=new Message();
                            message.obj=ifcap;
                            handler6.sendMessage(message);
                        }
                    }
                    @Override
                    public void onFailure(Call<HttpMessage<Boolean>> call, Throwable t) {
                    }
                });
                break;
            case R.id.club_join:
                new AlertDialog.Builder(this)
                        .setTitle("确定加入本社吗？")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(HttpUtil.httpUrl)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                ClubRequest request = retrofit.create(ClubRequest.class);
                                Call<HttpMessage> call = request.joinClub(User.currentLoginUser.getUid(),clubid);
                                call.enqueue(new Callback<HttpMessage>() {
                                    @Override
                                    public void onResponse(Call<HttpMessage> call, Response<HttpMessage> response) {
                                        HttpMessage<Boolean> data=response.body();
                                        if (data.getCode()==200){
//                                            boolean ifcap =(boolean) data.getData();
//                                            Message message=new Message();
//                                            message.obj=ifcap;
//                                            handler6.sendMessage(message);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<HttpMessage> call, Throwable t) {
                                        Log.i("加入社团","错了");
                                    }
                                });
                                joined=true;
                                rel_join.setVisibility(View.GONE);
                                gonggao.setVisibility(View.VISIBLE);
                                Toast.makeText(ClubGroup.this, "加入成功！" , Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }
}
