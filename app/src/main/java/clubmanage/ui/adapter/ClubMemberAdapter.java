package clubmanage.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import clubmanage.model.User;
import clubmanage.ui.R;
import clubmanage.util.ClubManageUtil;

public class ClubMemberAdapter extends RecyclerView.Adapter<ClubMemberAdapter.ViewHolder>{
    private List<User> userList;
    private Context mContext;
    private boolean iscap;
    private AlertDialog alert=null;
    private AlertDialog.Builder builder=null;
    private int clubid;
    private int index;

    public ClubMemberAdapter(List<User> user,boolean cap,int clubid){
        userList=user;
        iscap=cap;
        this.clubid=clubid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        if (iscap==true){
            holder.memberView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    final User user = userList.get(position);
                    final String[] fruits = new String[]{"转让社长", "删除社员"};
                    alert = null;
                    builder = new AlertDialog.Builder(mContext);
                    if(user.getUid().equals(User.currentLoginUser.getUid())==false){
                        alert = builder.setIcon(android.R.drawable.ic_menu_edit)
                                .setTitle("社团管理")
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (fruits[index].equals("转让社长")){
                                            new Thread(){
                                                @Override
                                                public void run() {
                                                    ClubManageUtil.clubManage.transferPresident(user.getUid(),User.currentLoginUser.getUid(),clubid);
                                                }
                                            }.start();
                                            iscap=false;
                                            Toast.makeText(mContext, "转让成功", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (fruits[index].equals("删除社员")){
                                            new Thread(){
                                                @Override
                                                public void run() {
                                                    ClubManageUtil.clubManage.deleteMember(user.getUid(),clubid);
                                                }
                                            }.start();
                                            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setSingleChoiceItems(fruits, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(mContext, "你选择了" + fruits[which], Toast.LENGTH_SHORT).show();
                                        index=which;
                                    }
                                }).create();
                        alert.show();
                    }
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user=userList.get(position);
        byte[] bt= Base64.decode(user.getImage(),Base64.DEFAULT);
        if(bt!=null){
            holder.head.setImageBitmap(BitmapFactory.decodeByteArray(bt, 0, bt.length));
        }
        else
            Glide.with(mContext).load(R.drawable.photo1).into(holder.head);
        String name=user.getName();
        if(user.getUid().equals(User.currentLoginUser.getUid())) name=name+"     我";
        holder.name.setText(name);
        if(user.getPhone_number()==null) holder.phone.setText("手机：暂无");
        else holder.phone.setText("手机："+user.getPhone_number());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View memberView;
        ImageView head;
        TextView name;
        TextView phone;
        public ViewHolder(View view){
            super(view);
            memberView=view;
            head=(ImageView)view.findViewById(R.id.club_member_head);
            name=(TextView)view.findViewById(R.id.club_member_name);
            phone=(TextView)view.findViewById(R.id.club_member_phone);
        }
    }
}
