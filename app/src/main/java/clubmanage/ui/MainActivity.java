package clubmanage.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import clubmanage.ui.club.ClubsPage;
import clubmanage.ui.home.HomePage;
import clubmanage.ui.manage.Manage_Fragement;
import clubmanage.ui.my.My_Fragement;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private HomePage fragment1;
    private ClubsPage fragment2;
    private Manage_Fragement fragment3;
    private My_Fragement fragment4;


    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }
    //初始化fragment和fragment数组
    private void initFragment()
    {
        fragment1 = new HomePage();
        fragment2 = new ClubsPage();
        fragment3 = new Manage_Fragement();
        fragment4 = new My_Fragement();

        fragments = new Fragment[]{fragment1,fragment2,fragment3,fragment4};
        lastfragment=0;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_view,fragment1).show(fragment1).commit();

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }
    //判断选择的菜单
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.nav_home:
                {
                    if(lastfragment!=0)
                    {
                        switchFragment(lastfragment,0);
                        lastfragment=0;
                    }
                    return true;
                }
                case R.id.nav_club:
                {
                    if(lastfragment!=1)
                    {
                        switchFragment(lastfragment,1);
                        lastfragment=1;
                    }
                    return true;
                }
                case R.id.nav_manage:
                {
                    if(lastfragment!=2)
                    {
                        switchFragment(lastfragment,2);
                        lastfragment=2;
                    }
                    return true;
                }
                case R.id.nav_my:
                {
                    if(lastfragment!=3)
                    {
                        switchFragment(lastfragment,3);
                        lastfragment=3;
                    }
                    return true;
                }
            }
            return false;
        }
    };
    //切换Fragment
    private void switchFragment(int lastfragment,int index)
    {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if(fragments[index].isAdded()==false)
        {
            transaction.add(R.id.main_view,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}
