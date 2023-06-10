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
import com.example.tiku51_55.Been.X;

import java.util.List;

public class WDSC_Adapter extends ArrayAdapter<X> {

    public WDSC_Adapter(@NonNull Context context, List<X> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyAdapter.HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item3,parent,false);
            hv=new MyAdapter.HV();
            hv.tv1=convertView.findViewById(R.id.username);
            hv.tv2=convertView.findViewById(R.id.name);
            hv.tv3=convertView.findViewById(R.id.tel);
            hv.tv4=convertView.findViewById(R.id.status);
            hv.tv5=convertView.findViewById(R.id.time);
            hv.iv=convertView.findViewById(R.id.tx);
            convertView.setTag(hv);

        }else {
            hv= (MyAdapter.HV) convertView.getTag();
        }
        X  xx=getItem(position);
        hv.tv2.setText(xx.getName());
        hv.tv3.setText(xx.getTel());
        if(xx.getName().equals("张三")){
            hv.tv4.setText("一般管理员");
            hv.tv1.setText("用户名：user1");
        }else if(xx.getName().equals("李四")){
            hv.tv4.setText("用户");
            hv.tv1.setText("用户名：user2");
        }
        else if(xx.getName().equals("王五")){
            hv.tv4.setText("用户");
            hv.tv1.setText("用户名：user3");
        }
        else if(xx.getName().equals("赵六")){
            hv.tv4.setText("用户");
            hv.tv1.setText("用户名：user4");
        }

        if(xx.getSex().equals("男")){
            hv.iv.setImageResource(R.drawable.touxiang_2);
        }else {
            hv.iv.setImageResource(R.drawable.touxiang_1);
        }

        hv.tv5.setText(xx.getTime());


        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        ImageView iv;
    }
}
