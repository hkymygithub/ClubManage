package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import clubmanage.model.Create_club;
import clubmanage.model.User;
import clubmanage.util.ClubManageUtil;

public class ReviewedClub extends AppCompatActivity implements View.OnClickListener {
    private List<CreateClubMsg> createClubMsgList=new ArrayList<>();
    private Create_club create_club;
    private int clubid;
    private EditText suggest;
    private TextView t_create_club_name;
    private TextView t_create_club_cat;
    private TextView t_create_club_place;
    private TextView t_create_club_introduce;
    private TextView t_create_club_reason;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            create_club=(Create_club) msg.obj;
            t_create_club_name.setText(create_club.getClub_name());
            t_create_club_cat.setText(create_club.getClub_category());
            t_create_club_place.setText(create_club.getArea_name());
            t_create_club_introduce.setText(create_club.getIntroduce());
            t_create_club_reason.setText(create_club.getReason());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewed_club);
        Toolbar toolbar = findViewById(R.id.audit_activity_toolbar_club);
        Intent intentget=getIntent();
        clubid=(int)intentget.getSerializableExtra("clubid");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t_create_club_name=findViewById(R.id.t_create_club_name);
        t_create_club_cat=findViewById(R.id.t_create_club_cat);
        t_create_club_place=findViewById(R.id.t_create_club_place);
        t_create_club_introduce=findViewById(R.id.t_create_club_introduce);
        t_create_club_reason=findViewById(R.id.t_create_club_reason);
        suggest=(EditText)findViewById(R.id.edit_text_audit_club);
        Button yes=(Button)findViewById(R.id.button_pass_audit_club);
        yes.setOnClickListener(this);
        Button no=(Button)findViewById(R.id.button_return_audit_club);
        no.setOnClickListener(this);
        getClub();
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
            case R.id.button_return_audit_club:
                new Thread(){
                    @Override
                    public void run() {
                        ClubManageUtil.applicationManage.feedbackClubAppli(create_club.getApplyclub_formid(),0, User.currentLoginUser.getUid(),suggest.getText().toString());
                    }
                }.start();
                Toast.makeText(this,"审核完成",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.button_pass_audit_club:
                new Thread(){
                    @Override
                    public void run() {
                        ClubManageUtil.applicationManage.feedbackClubAppli(create_club.getApplyclub_formid(),1, User.currentLoginUser.getUid(),suggest.getText().toString());
                    }
                }.start();
                Toast.makeText(this,"审核完成",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
    private void getClub(){
        Create_club club=ClubManageUtil.applicationManage.searchCreateClubAppliByID(clubid);
        Message message=new Message();
        message.obj=club;
        handler.sendMessage(message);
    }
}
