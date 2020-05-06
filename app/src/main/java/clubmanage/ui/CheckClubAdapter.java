package clubmanage.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import clubmanage.model.Create_club;

public class CheckClubAdapter extends RecyclerView.Adapter<CheckClubAdapter.ViewHolder>{
    private List<Create_club> checkClubMsgList;
    private Context mContext;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext==null)
            mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_club_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.activityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position<0) return;
                int clubid = checkClubMsgList.get(position).getApplyclub_formid();
                Intent intent=new Intent(mContext, ReviewedClub.class);
                intent.putExtra("clubid",clubid);
                mContext.startActivity(intent);
//                Toast.makeText(v.getContext(), "you clicked view " + activity.getAct_name(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Create_club club = checkClubMsgList.get(position);
        holder.clubname.setText(club.getClub_name());
        holder.clubcreattime.setText("申请人学号："+club.getUid());
        holder.actposition.setText("申请人姓名："+club.getApplican_name());
        holder.array.setImageResource(R.drawable.arrow_right);
        holder.clock.setImageResource(R.drawable.clock);
        holder.posi.setImageResource(R.drawable.map);
    }

    @Override
    public int getItemCount() {
        return checkClubMsgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View activityView;
        TextView clubname;
        TextView clubcreattime;
        TextView actposition;
        ImageView array;
        ImageView clock;
        ImageView posi;

        public ViewHolder(View view) {
            super(view);
            activityView = view;
            clubname = (TextView) view.findViewById(R.id.check_club_name);
            clubcreattime = (TextView) view.findViewById(R.id.check_club_time);
            actposition = (TextView) view.findViewById(R.id.check_club_position);
            array=(ImageView)view.findViewById(R.id.check_club_right_array);
            clock=(ImageView)view.findViewById(R.id.check_club_clock);
            posi=(ImageView)view.findViewById(R.id.check_club_posi);
        }
    }

    public CheckClubAdapter(List<Create_club> fruitList) {
        checkClubMsgList = fruitList;
    }
}
