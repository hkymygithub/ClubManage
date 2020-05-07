package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;

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

import androidx.appcompat.widget.Toolbar;

import clubmanage.control.Tools;
import clubmanage.httpInterface.PersonalRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.User;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangPwdActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText oldpwd;
    private EditText newpwd;
    private EditText newpwd2;
    private String exception=null;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            exception=(String) msg.obj;
            if(exception!=null){
                Toast.makeText(ChangPwdActivity.this,exception,Toast.LENGTH_SHORT).show();
                exception=null;
            }else {
                Toast.makeText(ChangPwdActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_pwd);
        Toolbar toolbar = findViewById(R.id.change_pwd_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button chgpwdButton=findViewById(R.id.button_change);
        chgpwdButton.setOnClickListener(this);;
        TextView uid=(TextView)findViewById(R.id.current_uid);
        uid.setText(User.currentLoginUser.getUid());
        oldpwd=(EditText)findViewById(R.id.edit_pwd1);
        newpwd=(EditText)findViewById(R.id.edit_pwd2);
        newpwd2=(EditText)findViewById(R.id.edit_pwd3);
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
            case R.id.button_change:
                if(oldpwd.getText().toString()==null||oldpwd.getText().toString().equals("")){
                    Toast.makeText(ChangPwdActivity.this,"旧密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpwd.getText().toString()==null||newpwd.getText().toString().equals("")){
                    Toast.makeText(ChangPwdActivity.this,"新密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpwd2.getText().toString()==null||newpwd2.getText().toString().equals("")){
                    Toast.makeText(ChangPwdActivity.this,"重复密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
//                new Thread(){
//                    @Override
//                    public void run() {
//                        try {
//                            ClubManageUtil.personalManage.changePwd(User.currentLoginUser.getUid(),oldpwd.getText().toString(),
//                                    newpwd.getText().toString(),newpwd2.getText().toString());
//                            Message message=new Message();
//                            message.obj=null;
//                            handler.sendMessage(message);
//                        } catch (BaseException e) {
//                            Message message=new Message();
//                            message.obj=e.getMessage();
//                            handler.sendMessage(message);
//                        }
//                    }
//                }.start();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://121.36.153.113:8000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PersonalRequest request = retrofit.create(PersonalRequest.class);
                Call<HttpMessage> call = request.changePwd(User.currentLoginUser.getUid(),oldpwd.getText().toString(),
                                    newpwd.getText().toString(),newpwd2.getText().toString());
                call.enqueue(new Callback<HttpMessage>() {
                    @Override
                    public void onResponse(Call<HttpMessage> call, Response<HttpMessage> response) {
                        HttpMessage<Integer> data=response.body();
                        if (data.getCode()==0){
                            Message message=new Message();
                            message.obj=null;
                            handler.sendMessage(message);
                        }else {
                            Message message=new Message();
                            message.obj=data.getMsg();
                            handler.sendMessage(message);
                        }
                    }
                    @Override
                    public void onFailure(Call<HttpMessage> call, Throwable t) {
                    }
                });
                break;
        }
    }
}
