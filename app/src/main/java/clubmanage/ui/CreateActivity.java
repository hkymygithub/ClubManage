package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import clubmanage.model.Area;
import clubmanage.model.Club;
import clubmanage.model.Create_activity;
import clubmanage.model.User;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private int clubid;
    private String[] place;
    private byte[] mbyteArray;
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private RelativeLayout create_name;
    private RelativeLayout create_cat;
    private RelativeLayout create_introduce;
    private RelativeLayout create_attention;
    private RelativeLayout create_public;
    private RelativeLayout create_place;
    private RelativeLayout create_start_time;
    private RelativeLayout create_finish_time;
    private RelativeLayout create_reason;
    private TextView t_create_name;
    private TextView t_create_cat;
    private TextView t_create_introduce;
    private TextView t_create_attention;
    private TextView t_create_public;
    private TextView t_create_place;
    private TextView t_create_start_time;
    private TextView t_create_finish_time;
    private TextView t_create_reason;

    private Handler handler2=new Handler(){
        public void handleMessage(Message msg){
            exception=(String) msg.obj;
            if (exception==null){
                Toast.makeText(CreateActivity.this, "创建成功，请等待审批", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                button.setEnabled(true);
                Toast.makeText(CreateActivity.this, exception, Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };
    private Handler handler3=new Handler(){
        public void handleMessage(Message msg){
            place=(String[])msg.obj;
        }
    };
    private String exception=null;
    private CircleImageView img;
    private Button button;

    public static final int CHOOSE_PHOTO = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Intent intentget=getIntent();
        clubid=(int)intentget.getSerializableExtra("clubid");
        verifyStoragePermissions(this);
        Toolbar toolbar = findViewById(R.id.create_activity_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        create_name=findViewById(R.id.create_name);
        create_cat=findViewById(R.id.create_cat);
        create_introduce=findViewById(R.id.create_introduce);
        create_attention=findViewById(R.id.create_attention);
        create_public=findViewById(R.id.create_public);
        create_place=findViewById(R.id.create_place);
        create_start_time=findViewById(R.id.create_start_time);
        create_finish_time=findViewById(R.id.create_finish_time);
        create_reason=findViewById(R.id.create_reason);
        create_name.setOnClickListener(this);
        create_cat.setOnClickListener(this);
        create_introduce.setOnClickListener(this);
        create_attention.setOnClickListener(this);
        create_public.setOnClickListener(this);
        create_place.setOnClickListener(this);
        create_start_time.setOnClickListener(this);
        create_finish_time.setOnClickListener(this);
        create_reason.setOnClickListener(this);
        t_create_name=findViewById(R.id.t_create_name);
        t_create_cat=findViewById(R.id.t_create_cat);
        t_create_introduce=findViewById(R.id.t_create_introduce);
        t_create_attention=findViewById(R.id.t_create_attention);
        t_create_public=findViewById(R.id.t_create_public);
        t_create_place=findViewById(R.id.t_create_place);
        t_create_start_time=findViewById(R.id.t_create_start_time);
        t_create_finish_time=findViewById(R.id.t_create_finish_time);
        t_create_reason=findViewById(R.id.t_create_reason);

        button=findViewById(R.id.button_create_activity);
        button.setOnClickListener(this);
        img=findViewById(R.id.create_activity_poster);
        img.setOnClickListener(this);
        getPlace();
    }

    public void getPlace(){
        new Thread(){
            @Override
            public void run() {
                List<Area> area=null;
                try {
                    area=ClubManageUtil.areaManage.listUsibleSpe();
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                String[] places=new String[area.size()];
                for(int i=0;i<area.size();i++){
                    places[i]=area.get(i).getArea_name();
                }
                Message message=new Message();
                message.obj=places;
                handler3.sendMessage(message);
            }
        }.start();
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

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_activity_poster:
                if (ContextCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
                break;
            case R.id.button_create_activity:
                if(t_create_cat.getText().toString().equals("")||t_create_introduce.getText().toString().equals("") ||t_create_finish_time.getText().toString().equals("")||
                        t_create_start_time.getText().toString().equals("")||t_create_name.getText().toString().equals("")|| t_create_place.getText().toString().equals("")||
                        t_create_reason.getText().toString().equals("")||t_create_public.getText().toString().equals("")){
                        Toast.makeText(this, "请完善所有信息", Toast.LENGTH_SHORT).show();
                        return;
                }
                button.setEnabled(false);
                createActivity();
                break;
            case R.id.create_name:
                final EditText edt1 = new EditText(this);
                edt1.setMinLines(1);
                edt1.setMaxLines(1);
                new AlertDialog.Builder(this)
                        .setTitle("输入活动名")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(edt1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                t_create_name.setText(edt1.getText());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.create_cat:
                final String[] items = {"学术创新", "公益服务", "体育运动"};
                new AlertDialog.Builder(this)
                        .setTitle("请选择类别")
                        .setItems(items,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                t_create_cat.setText(items[which]);
                            }
                        }).show();
                break;
            case R.id.create_introduce:
                final EditText edt2 = new EditText(this);
                edt2.setMinLines(1);
                edt2.setMaxLines(5);
                new AlertDialog.Builder(this)
                        .setTitle("输入活动介绍")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(edt2)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                t_create_introduce.setText(edt2.getText());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.create_attention:
                final EditText edt4 = new EditText(this);
                edt4.setMinLines(1);
                edt4.setMaxLines(5);
                new AlertDialog.Builder(this)
                        .setTitle("输入活动注意事项")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(edt4)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                t_create_attention.setText(edt4.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.create_public:
                final String[] items2 = {"是", "否"};
                new AlertDialog.Builder(this)
                        .setTitle("请选择是否公开")
                        .setItems(items2,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                t_create_public.setText(items2[which]);
                            }
                        }).show();
                break;
            case R.id.create_place:
                new AlertDialog.Builder(this)
                        .setTitle("请选择场地")
                        .setItems(place,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                t_create_place.setText(place[which]);
                            }
                        }).show();
                break;
            case R.id.create_start_time:
                new DatePickerDialog(this, 4, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        t_create_start_time.setText(year+"-"+(monthOfYear + 1)+"-"+dayOfMonth);
                    }
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.create_finish_time:
                new DatePickerDialog(this, 4, new DatePickerDialog.OnDateSetListener() {
                    // 绑定监听器(How the parent is notified that the date is set.)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        t_create_finish_time.setText(year+"-"+(monthOfYear + 1)+"-"+dayOfMonth);
                    }
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.create_reason:
                final EditText edt3 = new EditText(this);
                edt3.setMinLines(1);
                edt3.setMaxLines(5);
                new AlertDialog.Builder(this)
                        .setTitle("输入申请理由")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(edt3)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                t_create_reason.setText(edt3.getText());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }


    private void createActivity(){
        new Thread(){
            @Override
            public void run() {
                boolean p=true;
                if (t_create_public.equals("是"))p=true;
                else if (t_create_public.equals("否")) p=false;
                try {
                    ClubManageUtil.applicationManage.addActivityAppli(clubid,mbyteArray,
                            t_create_name.getText().toString(),t_create_place.getText().toString(),User.currentLoginUser.getUid(),
                            User.currentLoginUser.getName(),t_create_start_time.getText().toString()+" 00:00:00",
                            t_create_finish_time.getText().toString()+" 00:00:00", t_create_introduce.getText().toString(),
                            t_create_attention.getText().toString(),t_create_cat.getText().toString(),p,t_create_reason.getText().toString());
                    Message message=new Message();
                    message.obj=null;
                    handler2.sendMessage(message);
                } catch (BaseException e) {
                    Message message=new Message();
                    message.obj=e.getMessage();
                    handler2.sendMessage(message);
                }
            }
        }.start();
    }

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            mbyteArray=byteArray;
            img.setImageBitmap(bitmap);
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
