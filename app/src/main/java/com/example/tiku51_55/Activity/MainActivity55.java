package com.example.tiku51_55.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tiku51_55.R;
import com.example.tiku51_55.Fragment.ZT_1;
import com.example.tiku51_55.Fragment.ZT_2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity55 extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mVP;
    private Button B1;
    private Button B2;

    List<Fragment>list=new ArrayList<>();
    MyActivity myActivity;
    ZT_1 zt_1;
    ZT_2 zt_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main55);
        initView();
        myActivity=new MyActivity(this.getSupportFragmentManager(),list);
        mVP.setCurrentItem(2);
        mVP.setAdapter(myActivity);
    }

    private void initView() {
        mVP = (ViewPager) findViewById(R.id.VP);
        B1 = (Button) findViewById(R.id.b1);
        B2 = (Button) findViewById(R.id.b2);

        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                if(position==0){
                    B1.setBackgroundResource(R.drawable.bg3);
                    B2.setBackgroundResource(R.drawable.bg);
                    zt_1.updateData();

                }else {
                    B1.setBackgroundResource(R.drawable.bg);
                    B2.setBackgroundResource(R.drawable.bg3);
                    zt_2.updateData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        B2.setOnClickListener(this);
        B1.setOnClickListener(this);

        zt_1=new ZT_1();
        zt_2=new ZT_2();
        list.add(zt_1);
        list.add(zt_2);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.b1:
                    mVP.setCurrentItem(0);
                    break;
                case R.id.b2:
                    mVP.setCurrentItem(1);
                    break;
            }
    }


    private class MyActivity extends FragmentPagerAdapter {
        List<Fragment>list=new ArrayList<>();

        public MyActivity(@NonNull FragmentManager fm,List<Fragment>list) {
            super(fm);
            this.list=list;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}