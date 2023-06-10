package com.example.tiku51_55.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Been.Car;
import com.example.tiku51_55.R;
import com.example.tiku51_55.utility_class.H;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
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

public class MainActivity62 extends AppCompatActivity {

    private ImageView mFanhui;
    private com.example.tiku51_55.utility_class.RadarChart mRC;

    List<Car>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main62);
        initView();
        setRC();
        getData();
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
                    list=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Car>>(){}.getType());

                    System.out.println(list.size()+"/////////");

                    for (int i = 0; i < list.size(); i++) {
                        for (int j = 0; j < list.size(); j++) {
                            if(list.get(i).getCarnumber().equals(list.get(j).getCarnumber())  && i!=j){
                                list.remove(j);
                            }
                        }
                    }
                    System.out.println(list.size()+"************");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mRC = (com.example.tiku51_55.utility_class.RadarChart) findViewById(R.id.RC);
    }

    public void setRC(){

        // 设置雷达图描述
        mRC.getDescription().setEnabled(false);

        // 设置雷达网颜色
        mRC.setWebColor(Color.LTGRAY);
        mRC.setWebColorInner(Color.LTGRAY);
        mRC.setDrawAngleCircle(true);
        mRC.setAngleCircleColor(new int[] {
                Color.parseColor("#36a9ce"),
                Color.parseColor("#33ff66"),
                Color.parseColor("#ef5aa1"),
                Color.parseColor("#ff0000"),
                Color.parseColor("#6600ff")
        });

        // 添加雷达数据
        List<RadarEntry> entries = new ArrayList<>();
        entries.add(new RadarEntry(4));
        entries.add(new RadarEntry(3));
        entries.add(new RadarEntry(5));
        entries.add(new RadarEntry(2));
        entries.add(new RadarEntry(6));
        RadarDataSet dataSet = new RadarDataSet(entries, "Data set");
        dataSet.setColor(Color.BLUE);
        dataSet.setFillColor(Color.BLUE);
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(180);
        dataSet.setLineWidth(2f);
        dataSet.setDrawHighlightIndicators(false);
        RadarData radarData = new RadarData(dataSet);
        mRC.setData(radarData);
        dataSet.setDrawValues(false);
        XAxis xAxis=mRC.getXAxis();


        // 刷新
        mRC.invalidate();
    }
}