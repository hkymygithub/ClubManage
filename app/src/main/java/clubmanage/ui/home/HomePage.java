package clubmanage.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import clubmanage.ui.activity.ActiviytsPage;
import clubmanage.ui.adapter.FragmentAdapter;
import clubmanage.ui.R;

public class HomePage extends Fragment implements View.OnClickListener{
    private ArrayList<Integer> imgs ;
    private ArrayList<String> titles ;
    private Banner banner;
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private String [] title={"社团","活动"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home,container,false);
        pager=view.findViewById(R.id.home_page);
        tabLayout=view.findViewById(R.id.home_tab_layout);
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
        fragmentList.add(new Home_Club(title[0]));
        fragmentList.add(new Home_Activity(title[1]));

        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);

        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系

        Button allButton=getActivity().findViewById(R.id.home_neck_btn3);
        allButton.setOnClickListener(this);

        imgs=feedImg();
        titles=feedImgTitle();
        banner = getActivity().findViewById(R.id.home_img_show);
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

    class ImageLoadBanner extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setImageResource(Integer.parseInt(path.toString()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_neck_btn3:
                Intent intent = new Intent(getContext(), ActiviytsPage.class);
                getContext().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                break;
            default:break;
        }
    }
}
