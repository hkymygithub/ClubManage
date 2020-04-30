package clubmanage.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import clubmanage.model.Area;
import clubmanage.model.Create_activity;
import clubmanage.model.Create_club;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;

public class CreateClubAdapter extends RecyclerView.Adapter<CreateClubAdapter.ViewHolder>{
    private List<CreateClubMsg> createClubMsgList;
    private AppCompatActivity mContext;
    private String[] place;
    private boolean canEdit;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            place=(String[])msg.obj;
        }
    };
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_create_club_item, parent, false);
        new Thread(){
            @Override
            public void run() {
                List<Area> area=null;
                try {
                    area= ClubManageUtil.areaManage.listUsibleSpe();
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                String[] places=new String[area.size()];
                for(int i=0;i<area.size();i++){
                    places[i]=area.get(i).getArea_name();
                }
                Message message=new Message();
                message.obj=places;
                handler.sendMessage(message);
            }
        }.start();
        final ViewHolder holder = new ViewHolder(view);
        if (canEdit==true){
            holder.CreateClubView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    CreateClubMsg create_activity = createClubMsgList.get(position);
                    switch (create_activity.getKey()){
                        case "社团名":
                            final EditText edt1 = new EditText(mContext);
                            edt1.setMinLines(1);
                            edt1.setMaxLines(1);
                            new AlertDialog.Builder(mContext)
                                    .setTitle("输入社团名")
                                    .setIcon(android.R.drawable.ic_menu_edit)
                                    .setView(edt1)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Create_club.createClub.setClub_name(edt1.getText().toString());
                                            holder.CreateClubValue.setText(edt1.getText().toString());
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                            break;
                        case "社团分类":
                            final String[] items = {"兴趣爱好","学术竞赛", "体育运动"};
                            new AlertDialog.Builder(mContext)
                                    .setTitle("请选择类别")
                                    .setItems(items,new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            holder.CreateClubValue.setText(items[which]);
                                            Create_club.createClub.setClub_category(items[which]);
                                        }
                                    }).show();
                            break;
                        case "社团场地":
                            new AlertDialog.Builder(mContext)
                                    .setTitle("请选择场地")
                                    .setItems(place,new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            holder.CreateClubValue.setText(place[which]);
                                            Create_club.createClub.setArea_name(place[which]);
                                        }
                                    }).show();
                            break;
                        case "社团简介":
                            final EditText edt3 = new EditText(mContext);
                            edt3.setMinLines(1);
                            edt3.setMaxLines(10);
                            new AlertDialog.Builder(mContext)
                                    .setTitle("输入社团简介")
                                    .setIcon(android.R.drawable.ic_menu_edit)
                                    .setView(edt3)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Create_club.createClub.setIntroduce(edt3.getText().toString());
                                            holder.CreateClubValue.setText(edt3.getText().toString());
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                            break;
                        case "创建理由":
                            final EditText edt4 = new EditText(mContext);
                            edt4.setMinLines(1);
                            edt4.setMaxLines(10);
                            new AlertDialog.Builder(mContext)
                                    .setTitle("输入创建理由")
                                    .setIcon(android.R.drawable.ic_menu_edit)
                                    .setView(edt4)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Create_club.createClub.setReason(edt4.getText().toString());
                                            holder.CreateClubValue.setText(edt4.getText().toString());
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                            break;
                    }
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CreateClubMsg CreateClub = createClubMsgList.get(position);
        holder.CreateClubKey.setText(CreateClub.getKey());
        holder.CreateClubValue.setText(CreateClub.getValue());
        if (canEdit==true)
        holder.CreateClubImage.setImageResource(R.drawable.arrow_right_grey);
    }

    @Override
    public int getItemCount() {
        return createClubMsgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View CreateClubView;
        TextView CreateClubKey;
        TextView CreateClubValue;
        ImageView CreateClubImage;

        public ViewHolder(View view) {
            super(view);
            CreateClubView = view;
            CreateClubKey = (TextView) view.findViewById(R.id.club_key);
            CreateClubValue = (TextView) view.findViewById(R.id.club_value);
            CreateClubImage = (ImageView) view.findViewById(R.id.club_img);
        }
    }

    public CreateClubAdapter(List<CreateClubMsg> fruitList,AppCompatActivity context,boolean canEdit) {
        createClubMsgList = fruitList;
        mContext=context;
        this.canEdit=canEdit;
    }
}
