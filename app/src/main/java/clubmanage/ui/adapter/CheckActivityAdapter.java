package clubmanage.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import clubmanage.model.Create_activity;
import clubmanage.ui.R;
import clubmanage.ui.ReviewedActivity;

public class CheckActivityAdapter extends RecyclerView.Adapter<CheckActivityAdapter.ViewHolder>{
    private List<Create_activity> checkMsgList;
    private Context mContext;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext==null)
            mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.activityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position<0) return;
                int activityid = checkMsgList.get(position).getActivity_approval_id();
                Intent intent=new Intent(mContext, ReviewedActivity.class);
                intent.putExtra("activityid",activityid);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Create_activity act = checkMsgList.get(position);
        holder.actname.setText(act.getActivity_name());
        int syear = act.getActivity_start_time().getYear()+1900;
        int smonth = act.getActivity_start_time().getMonth() + 1;
        int sday = act.getActivity_start_time().getDate();
        int eyear=act.getActivity_end_time().getYear()+1900;
        int emonth=act.getActivity_end_time().getMonth()+1;
        int eday=act.getActivity_end_time().getDay();
        holder.acttime.setText(syear+"-"+smonth+"-"+sday+"至"+eyear+"-"+emonth+"-"+eday);
        holder.actposition.setText(act.getArea_name());
        if (act.getPoster()==null) holder.actImage.setImageResource(R.drawable.enrollment);
        else {
            byte[] bt= Base64.decode(act.getPoster(),Base64.DEFAULT);
            holder.actImage.setImageBitmap(BitmapFactory.decodeByteArray(bt, 0, bt.length));
        }
        holder.array.setImageResource(R.drawable.arrow_right);
        holder.clock.setImageResource(R.drawable.clock);
        holder.posi.setImageResource(R.drawable.map);
    }

    @Override
    public int getItemCount() {
        return checkMsgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View activityView;
        TextView actname;
        TextView acttime;
        TextView actposition;
        ImageView actImage;
        ImageView array;
        ImageView clock;
        ImageView posi;

        public ViewHolder(View view) {
            super(view);
            activityView = view;
            actname = (TextView) view.findViewById(R.id.activity_name);
            acttime = (TextView) view.findViewById(R.id.activity_time);
            actposition = (TextView) view.findViewById(R.id.activity_position);
            actImage = (ImageView) view.findViewById(R.id.act_img);
            array=(ImageView)view.findViewById(R.id.array);
            clock=(ImageView)view.findViewById(R.id.clock);
            posi=(ImageView)view.findViewById(R.id.posi);
        }
    }

    public CheckActivityAdapter(List<Create_activity> fruitList) {
        checkMsgList = fruitList;
    }
}
