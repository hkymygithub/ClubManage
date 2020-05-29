package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.List;

import clubmanage.httpInterface.UserRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.Activity;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;
import clubmanage.util.HttpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText uid;
    EditText pwd1;
    EditText pwd2;
    EditText name;
    EditText mail;
    EditText phone;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            exception =(String)msg.obj;
            if (exception!=null){
                new AlertDialog.Builder(Register.this)
                        .setTitle(exception)
                        .setIcon(R.drawable.exclamation_mark)
                        .setPositiveButton("确定", null)
                        .show();
                return;
            }else {
                Toast.makeText(Register.this, "注册成功，请登录" , Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Register.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                Intent intent1=new Intent();
                intent1.putExtra("data","OK");
                setResult(RESULT_OK,intent1);
                finish();
            }
        }
    };
    String exception=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.reg_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button regButton=findViewById(R.id.button_register);
        regButton.setOnClickListener(this);
        uid=findViewById(R.id.edit_uid);
        pwd1=findViewById(R.id.edit_pwd1);
        pwd2=findViewById(R.id.edit_pwd2);
        name=findViewById(R.id.edit_name);
        mail=findViewById(R.id.edit_mail);
        phone=findViewById(R.id.edit_phone);
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtil.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserRequest request = retrofit.create(UserRequest.class);
        Call<HttpMessage> call = request.reg(uid.getText().toString(),pwd1.getText().toString(),
                            pwd2.getText().toString(),name.getText().toString(),mail.getText().toString(),
                            phone.getText().toString());
        call.enqueue(new Callback<HttpMessage>() {
            @Override
            public void onResponse(Call<HttpMessage> call, Response<HttpMessage> response) {
                HttpMessage data=response.body();
                if (data.getCode()==200){
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
    }
    @Override
    public void onBackPressed(){
        Intent intentback=new Intent();
        intentback.putExtra("data","No");
        setResult(RESULT_CANCELED);
        finish();
    }
}
