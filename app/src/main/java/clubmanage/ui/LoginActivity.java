package clubmanage.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import clubmanage.model.Club;
import clubmanage.model.User;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            User a=(User)msg.obj;
            User.currentLoginUser=a;
        }
    };
    private Handler handler2=new Handler(){
        public void handleMessage(Message msg){
            exception =(String)msg.obj;
            if(exception!=null){
                logButton.setEnabled(true);
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle(exception)
                        .setIcon(R.drawable.mark)
                        .setPositiveButton("确定", null)
                        .show();
                return;
            }else {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                Intent intent1=new Intent();
                intent1.putExtra("data","OK");
                setResult(RESULT_OK,intent1);
                finish();
            }
        }
    };
    EditText uid;
    EditText pwd;
    String exception=null;
    User user;
    Button logButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.reg_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        logButton=findViewById(R.id.button_login);
        logButton.setOnClickListener(this);
        uid=findViewById(R.id.edit_uid);
        pwd=findViewById(R.id.edit_pwd_login);
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
        logButton.setEnabled(false);
        new Thread(){
            @Override
            public void run() {
                try {
                    String id=uid.getText().toString();
                    String p=pwd.getText().toString();
                    user=ClubManageUtil.usermanage.login(uid.getText().toString(),pwd.getText().toString());
                    Message message=new Message();
                    message.obj=user;
                    handler.sendMessage(message);
                    Message message2=new Message();
                    message2.obj=null;
                    handler2.sendMessage(message2);
                } catch (BaseException e) {
                    Message message2=new Message();
                    message2.obj=e.getMessage();
                    handler2.sendMessage(message2);
                }
            }
        }.start();
    }
    @Override
    public void onBackPressed(){
        Intent intentback=new Intent();
        intentback.putExtra("data","No");
        setResult(RESULT_CANCELED);
        finish();
    }
}