package com.example.tiku51_55.Activity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Adapter.Gv_Adapter1;
import com.example.tiku51_55.Adapter.Gv_Adapter2;
import com.example.tiku51_55.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity60 extends AppCompatActivity {

    private ImageView mFanhui;
    private GridView mGv1;
    private GridView mGv2;

    List<String>list1=new ArrayList<>();
    List<String>list2=new ArrayList<>();
    Gv_Adapter1 adapter1;
    Gv_Adapter2 adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main60);
        initView();
        setGv2();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mGv1 = (GridView) findViewById(R.id.gv1);
        mGv2 = (GridView) findViewById(R.id.gv2);
    }
    public void setGv2(){
        list2.add("推荐");
        list2.add("安全驾驶");
        list2.add("交通分类");
        list2.add("科技类");
        list2.add("路况类");
        list2.add("汽车类");
        list2.add("二手车类");
        list2.add("改装车");
        list2.add("违章");

        adapter2=new Gv_Adapter2(this,list2);
        adapter1=new Gv_Adapter1(this,list1);
        mGv1.setAdapter(adapter1);
        adapter1.setMyclock(new Gv_Adapter1.Myclock() {
            @Override
            public void c(String i) {
                for (int j = 0; j < list1.size(); j++) {
                    if(list1.get(j).equals(i)){
                        list1.remove(j);
                        list2.add(i);
                        adapter2.notifyDataSetChanged();
                        adapter1.notifyDataSetChanged();
                    }
                }
            }
        });
        mGv2.setAdapter(adapter2);
        adapter2.setMyclock(new Gv_Adapter2.Myclock() {
            @Override
            public void c(String i) {
                for (int j = 0; j < list2.size(); j++) {
                    if(list2.get(j).equals(i)){
                        list2.remove(j);
                        list1.add(i);
                        adapter2.notifyDataSetChanged();
                        adapter1.notifyDataSetChanged();
                    }
                }


            }
        });


    }
}