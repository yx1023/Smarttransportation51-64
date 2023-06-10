package com.example.tiku51_55.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Been.GSGG;
import com.example.tiku51_55.R;
import com.example.tiku51_55.utility_class.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity64 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mFanhui;
    private ViewFlipper mViewFlipper;
    private ImageView mCz;
    private ImageView mYe;
    private ImageView mJl;
    private List<GSGG>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main64);
        initView();
        getNews();
    }

    public void getNews(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_news", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<GSGG>>(){}.getType());


                    setFlipper();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setFlipper() {
        for (int i = 0; i < list.size(); i++) {
            final GSGG c = list.get(i);
            TextView textView = new TextView(this);
            textView.setText(c.getTitle());
            textView.setTextColor(Color.BLACK);

            textView.setTextSize(30);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mViewFlipper.addView(textView);
                }
            });

        }
        mViewFlipper.startFlipping();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        mCz = (ImageView) findViewById(R.id.cz);
        mYe = (ImageView) findViewById(R.id.ye);
        mJl = (ImageView) findViewById(R.id.jl);
        mCz.setOnClickListener(this);
        mYe.setOnClickListener(this);
        mJl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.cz:
                intent.setClass(this, ETC_CZ.class);
                startActivity(intent);
                break;
            case R.id.ye:
                intent.setClass(this, ETC_YUE.class);
                startActivity(intent);
                break;
            case R.id.jl:
                intent.setClass(this, CZJL.class);
                startActivity(intent);
                break;
        }
    }
}