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

import com.example.tiku51_55.Been.XC;
import com.example.tiku51_55.R;

import java.util.List;

public class ETC_YUE_Atapter extends ArrayAdapter<XC> {
    public ETC_YUE_Atapter(@NonNull Context context, List<XC> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.yue_item,parent,false);
            hv=new HV();
            hv.iv=convertView.findViewById(R.id.iv);
            hv.money=convertView.findViewById(R.id.money);
            hv.number=convertView.findViewById(R.id.number);
            hv.plate=convertView.findViewById(R.id.plate);
            hv.name=convertView.findViewById(R.id.name);
            convertView.setTag(hv);
        }else{
            hv= (HV) convertView.getTag();
        }
        XC xc=getItem(position);
        if(xc.getBrand().equals("奔驰")){
            hv.iv.setImageResource(R.drawable.benci);
        } else if (xc.getBrand().equals("奥迪")) {
            hv.iv.setImageResource(R.drawable.audi);
        }else if (xc.getBrand().equals("宝马")) {
            hv.iv.setImageResource(R.drawable.bmw);
        }else if (xc.getBrand().equals("中华")) {
            hv.iv.setImageResource(R.drawable.zhonghua);
        }
        hv.name.setText("车主："+xc.getOwner());
        hv.plate.setText(xc.getPlate());
        hv.number.setText(xc.getNumber()+"");
        hv.money.setText("余额："+xc.getBalance());

        return convertView;
    }

    static class HV{
        TextView number;
        TextView money;
        TextView plate;
        TextView name;
        ImageView iv;
    }
}
