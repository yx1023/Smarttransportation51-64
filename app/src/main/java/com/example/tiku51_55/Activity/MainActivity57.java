package com.example.tiku51_55.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.utility_class.H;
import com.example.tiku51_55.Been.Hjjc;
import com.example.tiku51_55.Been.HjjcBean;
import com.example.tiku51_55.Adapter.Hjjc_Adapter;
import com.example.tiku51_55.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity57 extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mTime;
    private TextView mZjgx;
    private TextView mTvCityName;
    private ListView mListView;
    private PieChart mPC;
    private List<HjjcBean> hjjcBeans;
    private final Handler mHandler = new Handler();
    private Date date=new Date();
    private List<PieEntry> pieEntries;
    private Map<String, List<Hjjc>> mapAll;
    private Hjjc_Adapter adapter;
    private Map<String, Hjjc> map;
    private int  select = 0;
    private String[] arr = {"雄安", "北京", "上海", "深圳", "重庆"};
    private int[] colors = {Color.parseColor("#B22125"), Color.parseColor("#233543")
            , Color.parseColor("#509098"), Color.parseColor("#C86D53"), Color.parseColor("#80BD9F")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main57);
        initView();
        mHandler.post(mUpdataTimeRunable);
        mapAll = new HashMap<>();
        mPC.getDescription().setEnabled(false);
        Legend legend = mPC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setFormSize(25);
        legend.setTextSize(25);

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setDate();
            }
        },0,5000);

        setClick();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mTime = (TextView) findViewById(R.id.time);
        mZjgx = (TextView) findViewById(R.id.zjgx);
        mTvCityName = (TextView) findViewById(R.id.tv_city_name);
        mListView = (ListView) findViewById(R.id.list_view);
        mPC = (PieChart) findViewById(R.id.PC);
    }
    private void setClick() {
        mPC.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                select = (int) h.getX();
                setSelect();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
    private Runnable mUpdataTimeRunable=new Runnable() {
        @Override
        public void run() {
          setUpTime();
          mHandler.postDelayed(this,60*1000);
        }
    };

    public void setUpTime(){
        String uptime="最近更新：最新数据";
        long time=date.getTime();
        if(System.currentTimeMillis()-time> 120 * 1000 ){
            uptime="最近更新："+(System.currentTimeMillis()-time)/60/1000+"分钟之前";
        }
        mZjgx.setText(uptime);
        SimpleDateFormat format=new SimpleDateFormat("yyyy年M月d日 H:mm");
        String s=format.format(new Date());
        mTime.setText(s);
    }
    private void setDate() {
        if (map == null) map = new HashMap<>();
        else map.clear();
        for (int i = 0; i < 5; i++) {
            getData(i);
        }
    }
    public void getData(int i){
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
                    List<Hjjc>list=mapAll.get(arr[i]);
                    if(list==null){
                        list=new ArrayList<>();
                    }
                    list.add(0,new Gson().fromJson(jsonObject1.toString(),Hjjc.class));
                    mapAll.put(arr[i],list);
                    map.put(arr[i],list.get(0));

                    if(map.size()==5){
                        setSelect();
                        setPieChart();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setPieChart() {
        if (pieEntries == null) pieEntries = new ArrayList<>();
        else pieEntries.clear();
        for (int i = 0; i < map.size(); i++) {
            Hjjc hjjcs = map.get(arr[i]);
            pieEntries.add(new PieEntry(hjjcs.getPm25(), arr[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(colors);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.5f);
        dataSet.setValueLinePart1OffsetPercentage(70);
        dataSet.setValueLineColor(Color.BLACK);
        dataSet.setValueTextSize(10);
        dataSet.setValueFormatter(new PercentFormatter() {

            @Override
            public String getFormattedValue(float value) {
                if (value <= 35) {
                    return "优:" + value;
                }else if (value>35&&value<=75){
                    return "良:"+value;
                }else if (value>75&&value<=115){
                    return "轻度污染:"+value;
                }else if (value>115&&value<=150){
                    return "中度污染:"+value;
                }else {
                    return "重度污染:"+value;
                }
            }

        });
        PieData data = new PieData(dataSet);
        mPC.setData(data);
        mPC.setDrawHoleEnabled(false);
        mPC.setEntryLabelColor(Color.BLACK);
        mPC.invalidate();
    }
    private void setSelect() {
        mTvCityName.setText(arr[select]);
        if (mapAll.size() != 5) {
            return;
        }
        List<Hjjc> hjjcs = mapAll.get(arr[select]);
        List<Integer> wds = new ArrayList<>();
        List<Integer> cos = new ArrayList<>();
        List<Integer> pms = new ArrayList<>();
        List<Integer> gzs = new ArrayList<>();
        List<Integer> sds = new ArrayList<>();
        int wd = 0, sd = 0, gz = 0, pm = 0, co = 0;
        for (int i = 0; i < hjjcs.size(); i++) {
            Hjjc hjjc = hjjcs.get(i);
            wds.add(hjjc.getTemperature());
            wd += hjjc.getTemperature();
            cos.add(hjjc.getCo2());
            co += hjjc.getCo2();
            pms.add(hjjc.getPm25());
            pm += hjjc.getPm25();
            gzs.add(hjjc.getIllumination());
            gz += hjjc.getIllumination();
            sds.add(hjjc.getHumidity());
            sd += hjjc.getHumidity();
        }
        if (hjjcBeans == null) hjjcBeans = new ArrayList<>();
        else hjjcBeans.clear();
        hjjcBeans.add(new HjjcBean("pm2.5(µg/m3)", Collections.max(pms), Collections.min(pms), pm / pms.size()));
        hjjcBeans.add(new HjjcBean("二氧化碳(ppm)", Collections.max(cos), Collections.min(cos), co / cos.size()));
        hjjcBeans.add(new HjjcBean("光照强度(SI)", Collections.max(gzs), Collections.min(gzs), gz / gzs.size()));
        hjjcBeans.add(new HjjcBean("湿度(RH)", Collections.max(sds), Collections.min(sds), sd / sds.size()));
        hjjcBeans.add(new HjjcBean("温度(C)", Collections.max(wds), Collections.min(wds), wd / wds.size()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter == null) {
                    adapter = new Hjjc_Adapter(MainActivity57.this, hjjcBeans);
                    mListView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
}