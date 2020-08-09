package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import clubmanage.httpInterface.ApplicationRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Create_activity;
import clubmanage.model.User;
import clubmanage.util.ClubManageUtil;
import clubmanage.util.HttpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewedActivity extends AppCompatActivity implements View.OnClickListener {
    private int activityid;
    private Create_activity activity;
    private EditText suggest;
    private CircleImageView poster;
    private TextView t_create_name;
    private TextView t_create_cat;
    private TextView t_create_introduce;
    private TextView t_create_attention;
    private TextView t_create_public;
    private TextView t_create_place;
    private TextView t_create_start_time;
    private TextView t_create_finish_time;
    private TextView t_create_reason;
    private Button yes;
    private Button no;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            activity=(Create_activity) msg.obj;
            if (activity.getPoster()==null) poster.setImageResource(R.drawable.enrollment);
            else {
                byte[] bt= Base64.decode(activity.getPoster(),Base64.DEFAULT);
                poster.setImageBitmap(BitmapFactory.decodeByteArray(bt, 0, bt.length));
            }
            t_create_name.setText(activity.getActivity_name());
            t_create_cat.setText(activity.getActivity_category());
            t_create_introduce.setText(activity.getActivity_details());
            t_create_attention.setText(activity.getActivity_attention());
            if (activity.getIf_public_activity()==0)
                t_create_public.setText("否");
            else t_create_public.setText("是");
            t_create_place.setText(activity.getArea_name());
            t_create_start_time.setText(activity.getActivity_start_time().toString());
            t_create_finish_time.setText(activity.getActivity_end_time().toString());
            t_create_reason.setText(activity.getReason());
            yes.setClickable(true);
            no.setClickable(true);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewed);
        Intent intentget=getIntent();
        activityid=(int)intentget.getSerializableExtra("activityid");
        poster=(CircleImageView)findViewById(R.id.activity_creat_poster);
        Toolbar toolbar = findViewById(R.id.audit_activity_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        t_create_name=findViewById(R.id.t_create_name);
        t_create_cat=findViewById(R.id.t_create_cat);
        t_create_introduce=findViewById(R.id.t_create_introduce);
        t_create_attention=findViewById(R.id.t_create_attention);
        t_create_public=findViewById(R.id.t_create_public);
        t_create_place=findViewById(R.id.t_create_place);
        t_create_start_time=findViewById(R.id.t_create_start_time);
        t_create_finish_time=findViewById(R.id.t_create_finish_time);
        t_create_reason=findViewById(R.id.t_create_reason);

        suggest=(EditText)findViewById(R.id.edit_text_audit);
        yes=(Button)findViewById(R.id.button_pass_audit);
        yes.setOnClickListener(this);
        yes.setClickable(false);
        no=(Button)findViewById(R.id.button_return_audit);
        no.setOnClickListener(this);
        no.setClickable(false);
        getActivaty();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_return_audit:
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(HttpUtil.httpUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApplicationRequest request = retrofit.create(ApplicationRequest.class);
                Call<HttpMessage> call = request.feedbackActivityAppli(activity.getActivity_approval_id(),0, User.currentLoginUser.getUid(),suggest.getText().toString());
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
                Toast.makeText(this,"审核完成",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.button_pass_audit:

                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(HttpUtil.httpUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApplicationRequest request2 = retrofit2.create(ApplicationRequest.class);
                Call<HttpMessage> call2 = request2.feedbackActivityAppli(activity.getActivity_approval_id(),1, User.currentLoginUser.getUid(),suggest.getText().toString());
                call2.enqueue(new Callback<HttpMessage>() {
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
                Toast.makeText(this,"审核完成",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    private void getActivaty(){
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApplicationRequest request = retrofit.create(ApplicationRequest.class);
        Call<HttpMessage<Create_activity>> call = request.searchCreateActivityAppliByID(activityid);
        call.enqueue(new Callback<HttpMessage<Create_activity>>() {
            @Override
            public void onResponse(Call<HttpMessage<Create_activity>> call, Response<HttpMessage<Create_activity>> response) {
                HttpMessage data=response.body();
                if (data.getCode()==200){
                    Message message=new Message();
                    message.obj=data.getData();
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<Create_activity>> call, Throwable t) {
            }
        });
    }
}
