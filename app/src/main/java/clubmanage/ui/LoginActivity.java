package clubmanage.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import clubmanage.httpInterface.UserRequest;
import clubmanage.message.HttpMessage;
import clubmanage.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

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
        login(uid.getText().toString(),pwd.getText().toString());
    }
    @Override
    public void onBackPressed(){
        Intent intentback=new Intent();
        intentback.putExtra("data","No");
        setResult(RESULT_CANCELED);
        finish();
    }

    public void login(String uid,String pwd){
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://121.36.153.113:8000") //基础url,其他部分在GetRequestInterface里
                .addConverterFactory(GsonConverterFactory.create()) //Gson数据转换器
                .build();

        //创建网络请求接口实例
        UserRequest request = retrofit.create(UserRequest.class);
        Call<HttpMessage<User>> call = request.login(uid,pwd);

        //发送网络请求(异步)
        call.enqueue(new Callback<HttpMessage<User>>() {
            @Override
            public void onResponse(Call<HttpMessage<User>> call, Response<HttpMessage<User>> response) {
                HttpMessage<User> message=response.body();
                if (message.getCode()==0){
                    User.currentLoginUser = (User)message.getData();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    Intent intent1=new Intent();
                    intent1.putExtra("data","OK");
                    setResult(RESULT_OK,intent1);
                    finish();
                }else if (message.getCode()==1){
                    logButton.setEnabled(true);
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle(message.getMsg())
                            .setIcon(R.drawable.mark)
                            .setPositiveButton("确定", null)
                            .show();
                }
            }
            @Override
            public void onFailure(Call<HttpMessage<User>> call, Throwable t) {
                Log.i("Login",t.getMessage());
            }
        });
    }
}