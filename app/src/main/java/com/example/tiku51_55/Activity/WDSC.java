package com.example.tiku51_55.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.utility_class.DBmarager;
import com.example.tiku51_55.R;
import com.example.tiku51_55.Adapter.WDSC_Adapter;
import com.example.tiku51_55.Been.X;

import java.util.ArrayList;
import java.util.List;

public class WDSC extends AppCompatActivity {

    private ImageView mFanhui;
    private ListView mLv;
    WDSC_Adapter adapter;
    List<X> list = new ArrayList<>();
    private LinearLayout mLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdsc);
        initView();
        DBmarager dBmarager = new DBmarager(this);
        boolean result = dBmarager.isExist("xx");
        if (result == false) {
            mLl.setVisibility(View.VISIBLE);
            mLv.setVisibility(View.INVISIBLE);
        } else {
            Cursor c = dBmarager.queryDB("xx", null, null, null, null, null, null, null);
            list = dBmarager.sendee(c);
            adapter = new WDSC_Adapter(this, list);
            mLv.setAdapter(adapter);
        }
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mLv = (ListView) findViewById(R.id.lv);
        mLl = (LinearLayout) findViewById(R.id.ll);
    }
}