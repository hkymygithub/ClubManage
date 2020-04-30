package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Timestamp;

import clubmanage.model.Activity;
import clubmanage.model.User;
import clubmanage.util.ClubManageUtil;

public class ActivityItem extends AppCompatActivity implements View.OnClickListener {
    private int activityid;
    private String clubname;
    private String ownname;
    private Activity activity;
    private boolean isSignUp=false;
    private Button join;

    private ImageView poster;
    private TextView name;
    private TextView address;
    private TextView time;
    private TextView time2;
    private TextView intruduction;
    private TextView notice;
    private TextView act_person;
    private TextView act_club;

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            isSignUp=(boolean)msg.obj;
            if (isSignUp==true){
                join.setEnabled(false);
                join.setText("已报名");
                join.setBackgroundResource(R.drawable.button_shape_gray);
            }else {
                join.setEnabled(true);
                join.setText("我要报名");
                join.setBackgroundResource(R.drawable.button_shape);
            }
        }
    };
    private Handler handler2=new Handler() {
        public void handleMessage(Message msg) {
            activity=(Activity)msg.obj;
            if (activity.getPoster()!=null){
                byte[] bt= activity.getPoster();
                poster.setImageBitmap(BitmapFactory.decodeByteArray(bt, 0, bt.length));
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if(activity.getActivity_end_time().before(now)){
                join.setEnabled(false);
                join.setText("活动已结束");
                join.setBackgroundResource(R.drawable.button_shape_gray);
            }
            else {
                join.setEnabled(true);
                join.setText("我要报名");
                join.setBackgroundResource(R.drawable.button_shape);
            }
            name.setText(name.getText().toString()+" "+activity.getActivity_name());
            address.setText(address.getText().toString()+" "+activity.getActivity_place());
            time.setText(time.getText().toString()+" "+activity.getActivity_start_time().toString());
            time2.setText(time2.getText().toString()+""+activity.getActivity_end_time().toString());
            intruduction.setText(intruduction.getText().toString()+" "+activity.getActivity_introduce());
            notice.setText(notice.getText().toString()+" "+activity.getActivity_attention());
        }
    };
    private Handler handler3=new Handler(){
        public void handleMessage(Message msg){
            clubname=(String) msg.obj;
            act_club.setText(act_club.getText().toString()+" "+clubname);
        }
    };
    private Handler handler4=new Handler(){
        public void handleMessage(Message msg){
            ownname=(String) msg.obj;
            act_person.setText(act_person.getText().toString()+" "+ownname);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intentget=getIntent();
        activityid=(int) intentget.getSerializableExtra("activityid");
        poster=(ImageView)findViewById(R.id.activity_poster);
        join=findViewById(R.id.activity_sign_up);
        join.setEnabled(false);
        join.setOnClickListener(this);
        name=findViewById(R.id.manage_item_text1);
        address=findViewById(R.id.act_address);
        time=findViewById(R.id.act_time);
        time2=findViewById(R.id.act_time2);
        intruduction=findViewById(R.id.act_intruduction_context);
        notice=findViewById(R.id.act_intruduction);
        act_person=findViewById(R.id.act_person);
        act_club=findViewById(R.id.act_club);
        ImageButton back=findViewById(R.id.act_head_img1);
        back.setOnClickListener(this);
        new Thread(){
            @Override
            public void run() {
                boolean isSignUp=ClubManageUtil.activityManage.if_participate(User.currentLoginUser.getUid(),activityid);
                Message message=new Message();
                message.obj=isSignUp;
                handler.sendMessage(message);
            }
        }.start();
        getActivity();
        getClubName();
        getOwnname();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_head_img1:
                finish();
                break;
            case R.id.activity_sign_up:
                join.setEnabled(false);
                signUpAvtivity(User.currentLoginUser.getUid());
                Toast.makeText(ActivityItem.this,"报名成功",Toast.LENGTH_SHORT).show();
                join.setText("已报名");
                join.setBackgroundResource(R.drawable.button_shape_gray);
                break;
        }
    }
    private void signUpAvtivity(String uid){
        new Thread(){
            @Override
            public void run() {
               ClubManageUtil.applicationManage.signupActivity(User.currentLoginUser.getUid(),activityid);
            }
        }.start();
    }

    private void getActivity(){
        new Thread(){
            @Override
            public void run() {
                Activity activity=ClubManageUtil.activityManage.searchActivityById(activityid);
                Message message=new Message();
                message.obj=activity;
                handler2.sendMessage(message);
            }
        }.start();
    }

    private void getClubName(){
        new Thread(){
            @Override
            public void run() {
                String cname=ClubManageUtil.activityManage.findClubNameByActivityId(activityid);
                Message message=new Message();
                message.obj=cname;
                handler3.sendMessage(message);
            }
        }.start();
    }

    private void getOwnname(){
        new Thread(){
            @Override
            public void run() {
                String uname=ClubManageUtil.activityManage.findProprieterNameByActivityId(activityid);
                Message message=new Message();
                message.obj=uname;
                handler4.sendMessage(message);
            }
        }.start();
    }
}
