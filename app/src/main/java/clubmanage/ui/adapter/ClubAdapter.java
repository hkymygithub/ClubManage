package clubmanage.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import clubmanage.Tools.BitmapAndBytes;
import clubmanage.model.Club;
import clubmanage.ui.ClubGroup;
import clubmanage.ui.R;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder> {
    private Context mContext;
    private List<Club> mClubList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View clubView;
        ImageView actImage;
        TextView actText1;
        TextView actText2;
        TextView actText3;
        TextView actText4;
        TextView actText5;
        TextView actText6;
        TextView actText7;
        TextView actText8;

        public ViewHolder(View view){
            super(view);
            clubView=view;
            actImage = (ImageView) view.findViewById((R.id.act_item_img1));
            actText1 = (TextView) view.findViewById((R.id.act_item_text1));
            actText2 = (TextView) view.findViewById((R.id.act_item_text2));
            actText3 = (TextView) view.findViewById((R.id.act_item_text3));
            actText4 = (TextView) view.findViewById((R.id.act_item_text4));
            actText5 = (TextView) view.findViewById((R.id.act_item_text5));
            actText6 = (TextView) view.findViewById((R.id.act_item_text6));
            actText7 = (TextView) view.findViewById((R.id.act_item_text7));
            actText8 = (TextView) view.findViewById((R.id.act_item_text8));
        }
    }

    public ClubAdapter(List<Club> clubList){
        mClubList = clubList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.club_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.clubView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position<0) return;
                int clubid=mClubList.get(position).getClub_id();
                Intent intent=new Intent(mContext, ClubGroup.class);
                intent.putExtra("clubid",clubid);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Club club = mClubList.get(position);
//        Glide.with(mContext).load(R.drawable.photo1).into(holder.actImage);
        if (club.getClub_icon()==null) holder.actImage.setImageResource(R.drawable.photo1);
        else holder.actImage.setImageBitmap(BitmapAndBytes.bytesToBitmap(Base64.decode(club.getClub_icon(),Base64.DEFAULT)));
        holder.actText1.setText(club.getClub_name());
        holder.actText2.setText("热门社团");
        holder.actText3.setText("成员"+club.getMember_number());
        holder.actText4.setText("粉丝1273");
        holder.actText5.setText("动漫");
        holder.actText6.setText("颜值");
        holder.actText7.setText("cosplay");
        holder.actText8.setText(club.getClub_introduce());
    }

    @Override
    public int getItemCount(){
        return mClubList.size();
    }
}