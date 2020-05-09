package clubmanage.ui.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import clubmanage.ui.CheckResultMsg;
import clubmanage.ui.R;

public class CheckResultAdapter extends RecyclerView.Adapter<CheckResultAdapter.ViewHolder>{
    private List<CheckResultMsg> checkResultMsgsList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_result_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.activityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CheckResultMsg activity = checkResultMsgsList.get(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckResultMsg act = checkResultMsgsList.get(position);
        holder.actname.setText(act.getAct_name());
        if (act.getOpinion()==null||act.getOpinion().equals("")) holder.opinion.setText("");
        else holder.opinion.setText("反馈信息："+act.getOpinion());
        byte[] bt=act.getAct_picid();
        if(bt!=null){
            holder.actImage.setImageBitmap(BitmapFactory.decodeByteArray(bt, 0, bt.length));
        }else {
            holder.actImage.setImageResource(R.drawable.enrollment);
        }
        holder.if_pass.setText(act.getIf_pass());
        if(act.getIf_pass()=="审核通过")
            holder.if_pass_img.setImageResource(R.mipmap.success_green);
        else if(act.getIf_pass()=="申请驳回")
            holder.if_pass_img.setImageResource(R.mipmap.failed_red);
        else if(act.getIf_pass()=="等待审核")
            holder.if_pass_img.setImageResource(R.mipmap.visible);
    }

    @Override
    public int getItemCount() {
        return checkResultMsgsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View activityView;
        TextView actname;
        TextView opinion;
        TextView if_pass;
        ImageView actImage;
        ImageView if_pass_img;

        public ViewHolder(View view) {
            super(view);
            activityView = view;
            actname = (TextView) view.findViewById(R.id.act_name);
            opinion = (TextView) view.findViewById(R.id.opinion);
            actImage = (ImageView) view.findViewById(R.id.act_image);
            if_pass_img=(ImageView)view.findViewById(R.id.if_pass_img);
            if_pass=(TextView)view.findViewById(R.id.if_pass);
        }
    }

    public CheckResultAdapter(List<CheckResultMsg> fruitList) {
        checkResultMsgsList = fruitList;
    }
}
