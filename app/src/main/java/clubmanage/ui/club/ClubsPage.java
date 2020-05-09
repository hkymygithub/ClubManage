package clubmanage.ui.club;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import clubmanage.ui.adapter.FragmentAdapter;
import clubmanage.ui.R;
import clubmanage.ui.SearchClub;

public class ClubsPage extends Fragment implements View.OnClickListener{

    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private String [] title={"全部类型","兴趣爱好","学术竞赛","体育运动"};

    //轮播
    ArrayList<Integer> imgs = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    Banner banner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.club,container,false);
        ImageButton search=view.findViewById(R.id.act_head_button1);
        search.setOnClickListener(this);
        pager=view.findViewById(R.id.act_page);
        tabLayout=view.findViewById(R.id.act_tab_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            mTitles.add(title[i]);
        }
        fragmentList.add(new Club_TabFragment1(title[0]));
        fragmentList.add(new Club_TabFragment2(title[1]));
        fragmentList.add(new Club_TabFragment3(title[2]));
        fragmentList.add(new Club_TabFragment4(title[3]));
        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系

        imgs=feedImg();
        titles=feedImgTitle();
        banner = getActivity().findViewById(R.id.act_img_show);
        banner.setImages(imgs);
        banner.setImageLoader(new ImageLoadBanner());
        banner.setBannerTitles(titles);
        banner.setDelayTime(2000);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerAnimation(Transformer.Accordion);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.start();
    }

    private ArrayList<Integer> feedImg(){
        ArrayList<Integer> img = new ArrayList<>();
        img.add(R.drawable.science_club);
        img.add(R.drawable.enrollment);
        img.add(R.drawable.culture_arts_festival);
        return img;
    }

    private ArrayList<String> feedImgTitle(){
        ArrayList<String> title = new ArrayList<>();
        title.add("科技协会招新");
        title.add("社团招新游园会");
        title.add("第六届社团文化艺术节");
        return title;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_head_button1:
                Intent intent=new Intent(getContext(), SearchClub.class);
                startActivity(intent);
                break;
        }
    }

    class ImageLoadBanner extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setImageResource(Integer.parseInt(path.toString()));
        }
    }

}

