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

import com.example.tiku51_55.Activity.CZJL;
import com.example.tiku51_55.Been.JL;
import com.example.tiku51_55.R;

import java.util.List;

public class CZJL_Adapter extends ArrayAdapter<JL> {
    public CZJL_Adapter(@NonNull Context context, List<JL> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.czjl_item,parent,false);
            hv=new HV();
            hv.money=convertView.findViewById(R.id.money);
            hv.time1=convertView.findViewById(R.id.time1);
            hv.time2=convertView.findViewById(R.id.time2);
            hv.name=convertView.findViewById(R.id.name);
            hv.week=convertView.findViewById(R.id.week);
            hv.plate=convertView.findViewById(R.id.plate);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        JL jl=getItem(position);
        hv.name.setText("充值人："+jl.getName());
        hv.week.setText(jl.getWeek());
        hv.time1.setText(jl.getTime1());
        hv.time2.setText(jl.getTime2());
        hv.money.setText("充值："+jl.getMoney());
        hv.plate.setText("车牌号："+jl.getPlate());

        return convertView;
    }

    static class HV{
        TextView time1;
        TextView name;
        TextView time2;
        TextView week;
        TextView money;
        TextView plate;


    }
}
