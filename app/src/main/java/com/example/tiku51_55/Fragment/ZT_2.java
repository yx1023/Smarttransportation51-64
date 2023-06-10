package com.example.tiku51_55.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku51_55.Been.Bus;
import com.example.tiku51_55.utility_class.H;
import com.example.tiku51_55.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ZT_2 extends Fragment {
    private ImageView imageView;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    List<Bus> list=new ArrayList<>();
    public void updateData() {
        getBus();
        getSense();
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    tv1.setText(list.get(0).getBus()+"号公交车："+list.get(0).getDistance()+"m");
                    tv2.setText(list.get(1).getBus()+"号公交车："+list.get(1).getDistance()+"m");
                    break;
                case 2:
                    tv3.setText("PM2.5："+pm25+"μg/m3，温度："+temperature+"℃");
                    tv4.setText("湿度："+humidity+"%，CO2："+co2+" PPM");

                    break;
            }
            return false;
        }
    });
    private int temperature;
    private int humidity;
    private int pm25;
    private int co2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zt_2,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView=view.findViewById(R.id.fanhui);
        tv1=view.findViewById(R.id.car1);
        tv2=view.findViewById(R.id.car2);
        tv3=view.findViewById(R.id.hj1);
        tv4=view.findViewById(R.id.hj2);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getBus();

                getSense();

            }
        },0,3000);

    }

    public void getBus(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_bus_stop_distance", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("联想大厦站").toString(),new TypeToken<List<Bus>>(){}.getType());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(1);
                        }
                    }).start();



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void getSense(){
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
                    temperature = jsonObject1.getInt("temperature");
                    humidity = jsonObject1.getInt("humidity");
                    pm25 = jsonObject1.getInt("pm25");
                    co2 = jsonObject1.getInt("co2");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(2);
                        }
                    }).start();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
