package com.example.tiku51_55.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tiku51_55.Been.CGQ;
import com.example.tiku51_55.utility_class.H;
import com.example.tiku51_55.R;
import com.example.tiku51_55.Adapter.RZCX_Adapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity53 extends AppCompatActivity {

    private ImageView mFanhui;
    private SwipeRefreshLayout mSw;
    private ListView mLv;
    RZCX_Adapter adapter;
    List<CGQ>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main53);
        initView();
        for (int i = 0; i < 4; i++) {
            getData();
        }



    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mSw = (SwipeRefreshLayout) findViewById(R.id.sw);
        mLv = (ListView) findViewById(R.id.lv);
        mSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.remove(3);
                list.remove(2);
                for (int i = 0; i < 2; i++) {
                    getData();
                }
                mSw.setRefreshing(false);
                Toast.makeText(MainActivity53.this, "已更新两条数据", Toast.LENGTH_SHORT).show();
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
        new H().sendResuilt("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    int temperature=jsonObject1.getInt("temperature");
                    int humidity=jsonObject1.getInt("humidity");
                    int illumination=jsonObject1.getInt("illumination");
                    int co2=jsonObject1.getInt("co2");
                    int pm25=jsonObject1.getInt("pm25");

                    CGQ cgq=new CGQ();
                    cgq.setCo2(co2);
                    cgq.setHumidity(humidity);
                    cgq.setIllumination(illumination);
                    cgq.setPm25(pm25);
                    cgq.setTemperature(temperature);
                    SimpleDateFormat format=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                    String time=format.format(new Date());
                    cgq.setTime(time);

                    list.add(cgq);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new RZCX_Adapter(MainActivity53.this,list);
                            mLv.setAdapter(adapter);
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}