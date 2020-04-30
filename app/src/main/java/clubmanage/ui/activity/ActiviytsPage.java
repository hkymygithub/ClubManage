package clubmanage.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import clubmanage.ui.FragmentAdapter;
import clubmanage.ui.R;
import clubmanage.ui.SearchActivity;

public class ActiviytsPage extends AppCompatActivity implements View.OnClickListener{

    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private String [] title={"全部类型","兴趣爱好","公益服务","体育运动"};

    //轮播
    ArrayList<Integer> imgs ;
    ArrayList<String> titles ;
    Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activiyts_page);
        ImageView search=findViewById(R.id.activity_title_btn1);
        search.setOnClickListener(this);

        pager=findViewById(R.id.activity_page);
        tabLayout=findViewById(R.id.activity_tab_layout);
        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            mTitles.add(title[i]);
        }

        fragmentList.add(new Activity_TabFragment1(title[0]));
        fragmentList.add(new Activity_TabFragment2(title[1]));
        fragmentList.add(new Activity_TabFragment3(title[2]));
        fragmentList.add(new Activity_TabFragment4(title[3]));

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);

        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系

        imgs=feedImg();
        titles=feedImgTitle();
        banner = findViewById(R.id.activity_img_show);
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
            case R.id.activity_title_btn1:
                Intent intent=new Intent(ActiviytsPage.this, SearchActivity.class);
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
