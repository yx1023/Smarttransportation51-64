package com.example.tiku51_55.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku51_55.Been.HjjcBean;
import com.example.tiku51_55.R;

import java.util.List;

public class Hjjc_Adapter extends ArrayAdapter<HjjcBean> {
    public Hjjc_Adapter(@NonNull Context context, List<HjjcBean> resource) {
        super(context, 0,resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.hjjc_item,parent,false);
            hv=new HV();
            hv.itemAverage=convertView.findViewById(R.id.item_average);
            hv.itemShzs=convertView.findViewById(R.id.item_shzs);
            hv.itemMax=convertView.findViewById(R.id.item_max);
            hv.itemMin=convertView.findViewById(R.id.item_min);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        HjjcBean hjjcBean=getItem(position);
        hv.itemShzs.setText(hjjcBean.getMag());
        hv.itemMax.setText(hjjcBean.getMax() + "");
        hv.itemMin.setText(hjjcBean.getMin() + "");
        hv.itemAverage.setText(hjjcBean.getAverage() + "");

        return convertView;
    }

    static class HV{
        TextView itemShzs;
        TextView itemMax;
        TextView itemMin;
        TextView itemAverage;
    }
}
