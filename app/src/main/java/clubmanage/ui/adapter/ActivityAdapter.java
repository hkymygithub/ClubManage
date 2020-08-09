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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.util.List;

import clubmanage.model.Activity;
import clubmanage.ui.ActivityItem;
import clubmanage.ui.R;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private Context mContext;
    private List<Activity> mHomeList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View homeView;
        ImageView homeImage1;
        TextView homeText1;
        TextView homeText2;
        TextView homeText3;
        TextView homeText4;

        public ViewHolder(View view){
            super(view);
            homeView=view;
            homeImage1 = (ImageView) view.findViewById((R.id.home_item_img1));
            homeText1 = (TextView) view.findViewById((R.id.home_item_text1));
            homeText2 = (TextView) view.findViewById((R.id.home_item_text2));
            homeText3 = (TextView) view.findViewById((R.id.home_item_text3));
            homeText4 = (TextView) view.findViewById((R.id.home_item_text4));
        }
    }

    public ActivityAdapter(List<Activity> HomeList){
        mHomeList = HomeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.homeView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position<0) return;
                int activityid=mHomeList.get(position).getActivity_id();
                Intent intent=new Intent(mContext, ActivityItem.class);
                intent.putExtra("activityid",activityid);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Activity home = mHomeList.get(position);
        if (home.getPoster()==null)
            Glide.with(mContext).load(R.drawable.sports_club).into(holder.homeImage1);
        else {
            byte[] bt= Base64.decode(home.getPoster(),Base64.DEFAULT);
            holder.homeImage1.setImageBitmap(BitmapFactory.decodeByteArray(bt, 0, bt.length));
        }
        holder.homeText1.setText(home.getActivity_name());
        holder.homeText2.setText(home.getActivity_place());
        holder.homeText3.setText(home.getActivity_start_time().toString());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(new Timestamp(Long.parseLong(home.getActivity_end_time())).before(now))
            holder.homeText4.setText("已结束");
        else holder.homeText4.setText("火热报名中");
    }

    @Override
    public int getItemCount(){
        return this.mHomeList.size();
    }
}