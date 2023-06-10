package com.example.tiku51_55.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku51_55.Been.Weather;
import com.example.tiku51_55.R;

import java.util.ArrayList;
import java.util.List;

public class TQ_Adapter extends ArrayAdapter<Weather> {

    private List<String>time;
    private List<String>rq;
    public TQ_Adapter(@NonNull Context context, List<Weather> resource,List<String>list,List<String>rq) {
        super(context, 0,resource);
        this.time=list;
        this.rq=rq;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.tq_item,parent,false);
            hv=new HV();
            hv.iv=convertView.findViewById(R.id.iv);
            hv.tv1=convertView.findViewById(R.id.tv1);
            hv.tv2=convertView.findViewById(R.id.tv2);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }


        Weather weather=getItem(position);
        hv.tv1.setText(time.get(position));
        hv.tv2.setText(rq.get(position));
        if(weather.getWeather().equals("晴")){
            hv.iv.setImageResource(R.drawable.qing);
        } else if (weather.getWeather().equals("小雨")) {
            hv.iv.setImageResource(R.drawable.xiaoyu);
        } else if (weather.getWeather().equals("阴")) {
            hv.iv.setImageResource(R.drawable.yin);
        }


        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        ImageView iv;
    }
}
