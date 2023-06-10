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

import com.example.tiku51_55.R;

import java.util.List;

public class Gv_Adapter1 extends ArrayAdapter<String> {

    public Gv_Adapter1(@NonNull Context context, List<String> resource) {
        super(context, 0,resource);
    }

    public interface Myclock{
        void c(String i);
    }

    public Myclock getMyclock() {
        return myclock;
    }

    public void setMyclock(Myclock myclock) {
        this.myclock = myclock;
    }

    private Myclock myclock;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.gv_item1,parent,false);
            hv=new HV();
            hv.iv=convertView.findViewById(R.id.iv);
            hv.tv=convertView.findViewById(R.id.tv);
            convertView.setTag(hv);

        }else {
            hv= (HV) convertView.getTag();
        }

        String s=getItem(position);
        hv.tv.setText(s);
        hv.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclock.c(s);
            }
        });
        return convertView;
    }

    static class HV{
        TextView tv;
        ImageView iv;
    }
}
