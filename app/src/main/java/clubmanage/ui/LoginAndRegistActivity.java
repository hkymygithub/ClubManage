package clubmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginAndRegistActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_regist);
        Button regButton=findViewById(R.id.user_reg);
        Button loginButton=findViewById(R.id.user_login);
        regButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_reg:
                Intent intent1=new Intent(LoginAndRegistActivity.this, Register.class);
                startActivityForResult(intent1,1);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                break;
            case R.id.user_login:
                Intent intent2=new Intent(LoginAndRegistActivity.this, LoginActivity.class);
                startActivityForResult(intent2,2);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    this.finish();
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    this.finish();
                }
                break;
        }
    }
}
