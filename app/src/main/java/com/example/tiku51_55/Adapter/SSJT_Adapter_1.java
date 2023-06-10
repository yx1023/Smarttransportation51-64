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

import com.example.tiku51_55.Been.DB_ssjt;
import com.example.tiku51_55.R;

import java.util.List;

public class SSJT_Adapter_1 extends ArrayAdapter<DB_ssjt> {
    public SSJT_Adapter_1(@NonNull Context context, List<DB_ssjt> resource) {
        super(context, 0,resource);
    }

    public interface Myclick{
        void c(int id);
    }

    public Myclick getMyclick() {
        return myclick;
    }

    public void setMyclick(Myclick myclick) {
        this.myclick = myclick;
    }

    Myclick myclick;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.ssjt_item1,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.number);
            hv.tv2=convertView.findViewById(R.id.xianlu);
            hv.iv=convertView.findViewById(R.id.sousuo);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        DB_ssjt ssjt=getItem(position);
        hv.tv1.setText(ssjt.getNumber()+"路");
        hv.tv2.setText("("+ssjt.getXianlu()+")");
        hv.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclick.c(ssjt.getNumber());
            }
        });
        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        ImageView iv;
    }
}
