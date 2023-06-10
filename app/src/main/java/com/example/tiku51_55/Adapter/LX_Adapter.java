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

import com.example.tiku51_55.Been.LX;
import com.example.tiku51_55.R;

import java.util.List;

public class LX_Adapter extends ArrayAdapter<LX> {
    public LX_Adapter(@NonNull Context context, List<LX> resource) {
        super(context,0, resource);
    }
    public interface MyClik{
        void clik(int id);
        void clike(int id,String s);
    }

    public MyClik getMyClik() {
        return myClik;
    }

    public void setMyClik(MyClik myClik) {
        this.myClik = myClik;
    }

   private MyClik myClik;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.lxzs_item,parent,false);
            hv=new HV();
            hv.iv=convertView.findViewById(R.id.iv_);
            hv.tv1=convertView.findViewById(R.id.dz);
            hv.tv2=convertView.findViewById(R.id.gm);
            hv.tv3=convertView.findViewById(R.id.pj);
            convertView.setTag(hv);

        }else {
            hv= (HV) convertView.getTag();
        }
        LX lx=getItem(position);
        hv.tv1.setText(lx.getName());
        hv.tv3.setText("票价：￥"+lx.getPrice());
        if(lx.getName().equals("故宫")){
            hv.iv.setImageResource(R.drawable.gugong1);
        }else {
            hv.iv.setImageResource(R.drawable.tiananmen);
        }
        hv.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClik.clik(position);
            }
        });
        hv.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClik.clike(position,lx.getName());
            }
        });


        return convertView;
    }

    static class HV{
        ImageView iv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }
}
