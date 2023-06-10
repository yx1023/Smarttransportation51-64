package com.example.tiku51_55.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku51_55.R;

import java.util.List;

public class SSJT_Adapter_2 extends ArrayAdapter<String> {
    public SSJT_Adapter_2(@NonNull Context context, List<String> resource) {
        super(context, 0,resource);
    }



    public void setLastClickedPosition(int lastClickedPosition) {
        this.lastClickedPosition = lastClickedPosition;
    }

    private int lastClickedPosition = -1;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.ssjt_item2,parent,false);
            hv=new HV();
            hv.number=convertView.findViewById(R.id.number);
            hv.tv=convertView.findViewById(R.id.site);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }

        String s=getItem(position);
        hv.number.setText((position+1)+"");
        hv.tv.setText(s);

        if(lastClickedPosition==position){
            hv.tv.setTextColor(Color.RED);
            hv.number.setTextColor(Color.RED);
            hv.number.setBackgroundResource(R.drawable.yuan2);
        }else {
            hv.tv.setTextColor(Color.BLACK);
            hv.number.setTextColor(Color.BLACK);
            hv.number.setBackgroundResource(R.drawable.yuan1);
        }


        return convertView;
    }


    static class HV{
        TextView number;
        TextView tv;
    }
}
