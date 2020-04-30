package clubmanage.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import clubmanage.model.User;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;

public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView img;
    private RelativeLayout person_uid;
    private RelativeLayout person_name;
    private RelativeLayout person_gender;
    private RelativeLayout person_phone;
    private RelativeLayout person_mail;
    private RelativeLayout person_major;
    private TextView t_person_uid;
    private TextView t_person_name;
    private TextView t_person_gender;
    private TextView t_person_phone;
    private TextView t_person_mail;
    private TextView t_person_major;

    public static final int CHOOSE_PHOTO = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    public PersonalCenterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        verifyStoragePermissions(this);
        Toolbar toolbar = findViewById(R.id.person_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        person_uid=findViewById(R.id.person_uid);
        person_name=findViewById(R.id.person_name);
        person_gender=findViewById(R.id.person_gender);
        person_phone=findViewById(R.id.person_phone);
        person_mail=findViewById(R.id.person_mail);
        person_major=findViewById(R.id.person_major);
        person_uid.setOnClickListener(this);
        person_name.setOnClickListener(this);
        person_gender.setOnClickListener(this);
        person_phone.setOnClickListener(this);
        person_mail.setOnClickListener(this);
        person_major.setOnClickListener(this);
        t_person_uid=findViewById(R.id.t_person_uid);
        t_person_name=findViewById(R.id.t_person_name);
        t_person_gender=findViewById(R.id.t_person_gender);
        t_person_phone=findViewById(R.id.t_person_phone);
        t_person_mail=findViewById(R.id.t_person_mail);
        t_person_major=findViewById(R.id.t_person_major);

        t_person_uid.setText(User.currentLoginUser.getUid());
        t_person_name.setText(User.currentLoginUser.getName());
        t_person_gender.setText(User.currentLoginUser.getGender());
        t_person_phone.setText(User.currentLoginUser.getPhone_number());
        t_person_mail.setText(User.currentLoginUser.getMail());
        t_person_major.setText(User.currentLoginUser.getMajor());

        img=findViewById(R.id.per_cen_head);
        img.setOnClickListener(this);
        byte[] bt=User.currentLoginUser.getImage();
        if(bt!=null){
            img.setImageBitmap(BitmapFactory.decodeByteArray(bt, 0, bt.length));
        }
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
            case R.id.per_cen_head:
                if (ContextCompat.checkSelfPermission(PersonalCenterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PersonalCenterActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            case R.id.person_name:
                final EditText edt1 = new EditText(this);
                edt1.setMinLines(1);
                edt1.setMaxLines(1);
                new AlertDialog.Builder(this)
                        .setTitle("修改姓名")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(edt1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                final String meg=edt1.getText().toString();
                                if(meg==null||meg.equals("")) {
                                    Toast.makeText(PersonalCenterActivity.this, "内容不能为空" , Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                new Thread(){
                                    @Override
                                    public void run() {
                                        try {
                                            ClubManageUtil.personalManage.changeName(User.currentLoginUser.getUid(), meg);
                                        } catch (BaseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                                t_person_name.setText(meg);
                                User.currentLoginUser.setName(meg);
                                Toast.makeText(PersonalCenterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.person_gender:
                final String[] items = {"男", "女"};
                new AlertDialog.Builder(this)
                        .setTitle("请选择性别")
                        .setItems(items,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String msg=items[which];
                                new Thread(){
                                    @Override
                                    public void run() {
                                        try {
                                            ClubManageUtil.personalManage.changeGender(User.currentLoginUser.getUid(), msg);
                                        } catch (BaseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                                t_person_gender.setText(msg);
                                User.currentLoginUser.setGender(msg);
                                Toast.makeText(PersonalCenterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.person_phone:
                final EditText edt2 = new EditText(this);
                edt2.setMinLines(1);
                edt2.setMaxLines(1);
                new AlertDialog.Builder(this)
                        .setTitle("修改手机")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(edt2)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                final String meg=edt2.getText().toString();
                                if(meg==null||meg.equals("")) {
                                    Toast.makeText(PersonalCenterActivity.this, "内容不能为空" , Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                new Thread(){
                                    @Override
                                    public void run() {
                                        try {
                                            ClubManageUtil.personalManage.changePhone_number(User.currentLoginUser.getUid(), meg);
                                        } catch (BaseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                                t_person_phone.setText(meg);
                                User.currentLoginUser.setPhone_number(meg);
                                Toast.makeText(PersonalCenterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.person_mail:
                final EditText edt3 = new EditText(this);
                edt3.setMinLines(1);
                edt3.setMaxLines(2);
                new AlertDialog.Builder(this)
                        .setTitle("修改邮箱")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(edt3)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                final String meg=edt3.getText().toString();
                                if(meg==null||meg.equals("")) {
                                    Toast.makeText(PersonalCenterActivity.this, "内容不能为空" , Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                new Thread(){
                                    @Override
                                    public void run() {
                                        try {
                                            ClubManageUtil.personalManage.changeMail(User.currentLoginUser.getUid(), meg);
                                        } catch (BaseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                                t_person_mail.setText(meg);
                                User.currentLoginUser.setMail(meg);
                                Toast.makeText(PersonalCenterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.person_major:
                final EditText edt4 = new EditText(this);
                edt4.setMinLines(1);
                edt4.setMaxLines(1);
                new AlertDialog.Builder(this)
                        .setTitle("修改专业")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setView(edt4)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                final String meg=edt4.getText().toString();
                                if(meg==null||meg.equals("")) {
                                    Toast.makeText(PersonalCenterActivity.this, "内容不能为空" , Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                new Thread(){
                                    @Override
                                    public void run() {
                                        try {
                                            ClubManageUtil.personalManage.changeMajor(User.currentLoginUser.getUid(), meg);
                                        } catch (BaseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                                t_person_major.setText(meg);
                                User.currentLoginUser.setMajor(meg);
                                Toast.makeText(PersonalCenterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
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
            updateImg(byteArray);
            User.currentLoginUser.setImage(byteArray);
            img.setImageBitmap(bitmap);
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateImg(final byte[] img){
        new Thread(){
            @Override
            public void run() {
                try {
                    ClubManageUtil.personalManage.changeImage(User.currentLoginUser.getUid(),img);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
