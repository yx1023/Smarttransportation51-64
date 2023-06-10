package com.example.tiku51_55.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku51_55.Been.CGQ;
import com.example.tiku51_55.R;

import java.util.List;

public class RZCX_Adapter extends ArrayAdapter<CGQ> {
    public RZCX_Adapter(@NonNull Context context, List<CGQ> resource) {
        super(context, 0,resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item1,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.co2);
            hv.tv2=convertView.findViewById(R.id.sd);
            hv.tv3=convertView.findViewById(R.id.pm);
            hv.tv4=convertView.findViewById(R.id.gz);
            hv.tv5=convertView.findViewById(R.id.wd);
            hv.tv6=convertView.findViewById(R.id.time);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        CGQ cgq=getItem(position);
        hv.tv1.setText(cgq.getCo2()+" ppm");
        hv.tv2.setText(cgq.getHumidity()+" %RH");
        hv.tv3.setText(cgq.getPm25()+" μg/m3");
        hv.tv4.setText(cgq.getIllumination()+" SI");
        hv.tv5.setText(cgq.getTemperature()+"℃");

        hv.tv6.setText(cgq.getTime());

        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
    }
}
