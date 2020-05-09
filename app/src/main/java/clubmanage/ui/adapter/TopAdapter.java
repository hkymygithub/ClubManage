package clubmanage.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import clubmanage.model.User;
import clubmanage.ui.R;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> {
    private Context mContext;
    private List<User> mTopList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView topImage;

        public ViewHolder(View view){
            super(view);
            topImage = (ImageView) view.findViewById((R.id.top_img));
        }
    }

    public TopAdapter(List<User> topList){
        mTopList = topList;
    }

    @Override
    public TopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.top_item,parent,false);
        return new TopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopAdapter.ViewHolder holder, int position){
        User top = mTopList.get(position);
        byte[] bt=null;
        if (top.getImage()!=null) bt= Base64.decode(top.getImage(),Base64.DEFAULT);
        if(bt!=null){
            holder.topImage.setImageBitmap(BitmapFactory.decodeByteArray(bt, 0, bt.length));
        }
        else
            Glide.with(mContext).load(R.drawable.photo1).into(holder.topImage);
    }

    @Override
    public int getItemCount(){
        return mTopList.size();
    }
}
