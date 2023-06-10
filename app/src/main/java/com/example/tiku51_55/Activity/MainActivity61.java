package com.example.tiku51_55.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Adapter.TQ_Adapter;
import com.example.tiku51_55.Been.Weather;
import com.example.tiku51_55.R;
import com.example.tiku51_55.utility_class.H;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity61 extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mWd;
    private TextView mTq;
    private TextView mTime;
    private GridView mGV;
    private LineChart mLC;

    private List<Weather> list = new ArrayList<>();

    List<Entry> entries1;
    List<Entry> entries2;

    List<Float> min = new ArrayList<>();
    List<Float> max = new ArrayList<>();

    TQ_Adapter adapter;

    private List<String> time = new ArrayList<>();
    private List<String> rq = new ArrayList<>();
    private String arr[] = {"周天", "周一", "周二", "周三", "周四", "周五", "周六"};
    private String arr1[] = {"昨天", "今天", "明天"};
    private ImageView mShuaxin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main61);
        initView();
        setTime();
        getData();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mWd = (TextView) findViewById(R.id.wd);
        mTq = (TextView) findViewById(R.id.tq);
        mTime = (TextView) findViewById(R.id.time);
        mGV = (GridView) findViewById(R.id.GV);
        mLC = (LineChart) findViewById(R.id.LC);

        mShuaxin = (ImageView) findViewById(R.id.shuaxin);
        mShuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                min.clear();
                max.clear();
                getData();
            }
        });
    }

    public void setTime() {
        Calendar calendar = Calendar.getInstance();
        time.add(arr1[0]);
        for (int i = 0; i < 5; i++) {
            if (i < 2) {
                time.add(arr1[i + 1]);
            } else {
                time.add(arr[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Calendar calr = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        calr.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calr.getTime());
        rq.add(yesterday);
        for (int i = 1; i < 6; i++) {
            calr.add(Calendar.DATE, 1);
            rq.add(sdf.format(calr.getTime()));
        }


    }

    public void getData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_weather_info", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    int temperature = jsonObject1.getInt("temperature");
                    String weather = jsonObject1.getString("weather");
                    list = new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<Weather>>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWd.setText(temperature + "°");
                            mTq.setText(weather);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                            String times = simpleDateFormat.format(new Date());
                            mTime.setText(times + "刷新");
                            adapter = new TQ_Adapter(MainActivity61.this, list, time, rq);
                            mGV.setAdapter(adapter);


                            for (int i = 0; i < list.size(); i++) {
                                String[] a = list.get(i).getInterval().split("~");
                                min.add(Float.valueOf(a[0]));
                                max.add(Float.valueOf(a[1]));
                            }
                            setZX(min, max);
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setZX(List<Float> min, List<Float> max) {

        entries1 = new ArrayList<>();
        entries2 = new ArrayList<>();
        for (int i = 0; i < min.size(); i++) {
            entries1.add(new Entry(i, min.get(i)));
        }
        for (int i = 0; i < max.size(); i++) {
            entries2.add(new Entry(i, max.get(i)));
        }
        LineDataSet lineDataSet1 = new LineDataSet(entries1, "1");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "2");
        LineData lineData1 = new LineData(lineDataSet1, lineDataSet2);
        mLC.setExtraOffsets(20, 20, 20, 20);

        mLC.setScaleEnabled(false);
        mLC.getDescription().setEnabled(false);
        mLC.getLegend().setEnabled(false);
        mLC.setExtraTopOffset(10);

        lineDataSet1.setCircleRadius(8);//设置圆点半径大小
        lineDataSet1.setLineWidth(5);//设置折线的宽度
        lineDataSet1.setColor(Color.GREEN);
        lineDataSet1.setValueTextSize(20);
        lineDataSet2.setValueTextSize(20);
        lineDataSet1.setDrawCircleHole(false);//设置是否空心
        lineDataSet1.setCircleColors(Color.BLUE);//依次设置每个圆点的颜色
        lineDataSet1.setMode(LineDataSet.Mode.LINEAR); // 设置折线类型
        lineDataSet2.setCircleRadius(8);//设置圆点半径大小
        lineDataSet2.setLineWidth(5);//设置折线的宽度
        lineDataSet2.setColor(Color.BLUE);
        lineDataSet2.setDrawCircleHole(false);//设置是否空心
        lineDataSet2.setCircleColors(Color.BLUE);//依次设置每个圆点的颜色

        lineDataSet2.setMode(LineDataSet.Mode.LINEAR); // 设置折线类型
        mLC.setData(lineData1);
        setYAxis();
        setXAxis();
        mLC.invalidate();

    }

    public void setYAxis() {
        mLC.getAxisRight().setEnabled(false); //不显示右侧Y轴
        mLC.getAxisLeft().setEnabled(true);//不显示左轴
        YAxis yAxis1 = mLC.getAxisLeft();
        yAxis1.setDrawAxisLine(false);
        yAxis1.setEnabled(false);

    }

    public void setXAxis() {


        XAxis xAxis = mLC.getXAxis();
        xAxis.setEnabled(false);


        xAxis.setDrawGridLines(false);

        xAxis.setGranularity(1f);
        xAxis.setTextSize(15);


    }
}