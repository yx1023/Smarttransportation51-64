package com.example.tiku51_55.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Adapter.ETC_YUE_Atapter;
import com.example.tiku51_55.Been.XC;
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

public class ETC_YUE extends AppCompatActivity {

    private ImageView mFanhui;
    private ListView mLvYue;

     List<XC>list=new ArrayList<>();
     ETC_YUE_Atapter atapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_yue);
        initView();
        getData();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mLvYue = (ListView) findViewById(R.id.lv_yue);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_vehicle", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<XC>>(){}.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            atapter=new ETC_YUE_Atapter(ETC_YUE.this,list);
                            mLvYue.setAdapter(atapter);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}