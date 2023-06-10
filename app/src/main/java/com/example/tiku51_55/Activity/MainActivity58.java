package com.example.tiku51_55.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Been.Car;
import com.example.tiku51_55.utility_class.H;
import com.example.tiku51_55.R;
import com.github.mikephil.charting.charts.LineChart;
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

public class MainActivity58 extends AppCompatActivity {

    private ImageView mFanhui;
    private ImageView mRili;
    private TextView mTv1;
    private ImageView mIv1;
    private TextView mTv2;
    private ImageView mIv2;
    private LineChart mLCT;
    List<Car>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main58);
        initView();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mRili = (ImageView) findViewById(R.id.rili);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mIv2 = (ImageView) findViewById(R.id.iv2);
        mLCT = (LineChart) findViewById(R.id.LCT);
    }
    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<Car>>(){}.getType());

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}