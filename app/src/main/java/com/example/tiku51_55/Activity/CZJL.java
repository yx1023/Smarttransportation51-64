package com.example.tiku51_55.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Adapter.CZJL_Adapter;
import com.example.tiku51_55.Been.JL;
import com.example.tiku51_55.R;
import com.example.tiku51_55.utility_class.DBmarager;

import java.util.ArrayList;
import java.util.List;

public class CZJL extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mZong;
    private ListView mLvJilu;

    List<JL>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czjl);
        initView();
        setLv();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mZong = (TextView) findViewById(R.id.zong);
        mLvJilu = (ListView) findViewById(R.id.lv_jilu);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setLv(){
        DBmarager dBmarager=new DBmarager(this);
        boolean b   =dBmarager.isExist("ETC");
        if(b==false){

        }else {
            Cursor c=dBmarager.queryDB("ETC",null,null,null,null,null,null,null);
            list=dBmarager.sendeeqa(c);
            int zong=0;
            for (int i = 0; i < list.size(); i++) {
                zong+= Integer.parseInt(list.get(i).getMoney());
            }
            mZong.setText("充值合计："+zong+"元");
            CZJL_Adapter adapter=new CZJL_Adapter(this,list);
            mLvJilu.setAdapter(adapter);
        }
    }
}