package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import clubmanage.model.Create_club;
import clubmanage.model.User;
import clubmanage.util.ClubManageUtil;

public class ReviewedClub extends AppCompatActivity implements View.OnClickListener {
    private List<CreateClubMsg> createClubMsgList=new ArrayList<>();
    private Create_club create_club;
    private EditText suggest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewed_club);
        Toolbar toolbar = findViewById(R.id.audit_activity_toolbar_club);
        Intent intentget=getIntent();
        create_club=(Create_club)intentget.getSerializableExtra("create_club");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initAuditClub();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_auditClub);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CreateClubAdapter adapter = new CreateClubAdapter(createClubMsgList,ReviewedClub.this,false);
        recyclerView.setAdapter(adapter);
        suggest=(EditText)findViewById(R.id.edit_text_audit_club);
        Button yes=(Button)findViewById(R.id.button_pass_audit_club);
        yes.setOnClickListener(this);
        Button no=(Button)findViewById(R.id.button_return_audit_club);
        no.setOnClickListener(this);
    }

    private void initAuditClub(){
        CreateClubMsg club_name=new CreateClubMsg("社团名",create_club.getClub_name());
        createClubMsgList.add(club_name);
        CreateClubMsg sort=new CreateClubMsg("分类",create_club.getClub_category());
        createClubMsgList.add(sort);
        CreateClubMsg arrangement=new CreateClubMsg("社团场地",create_club.getArea_name());
        createClubMsgList.add(arrangement);
        CreateClubMsg gonggao=new CreateClubMsg("社团简介",create_club.getIntroduce());
        createClubMsgList.add(gonggao);
        CreateClubMsg if_public=new CreateClubMsg("创建理由",create_club.getReason());
        createClubMsgList.add(if_public);
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
}
