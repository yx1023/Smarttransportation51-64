package com.example.tiku51_55.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku51_55.R;
import com.example.tiku51_55.Been.XX;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyAdapter extends ArrayAdapter<XX> {

    public MyAdapter(@NonNull Context context, List<XX> resource) {
        super(context, 0,resource);
    }

    public interface Myclick{
        void c(int position);
        void c2(String username,String name,String tel,String status,String time,String sex);
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
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.item2,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.username);
            hv.tv2=convertView.findViewById(R.id.name);
            hv.tv3=convertView.findViewById(R.id.tel);
            hv.tv4=convertView.findViewById(R.id.status);
            hv.tv5=convertView.findViewById(R.id.shoucang);
            hv.tv6=convertView.findViewById(R.id.shanchu);
            hv.iv=convertView.findViewById(R.id.tx);
            hv.hsv=convertView.findViewById(R.id.HV);
            convertView.setTag(hv);

        }else {
            hv= (HV) convertView.getTag();
        }
        XX xx=getItem(position);


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
        SimpleDateFormat format=new SimpleDateFormat("yyyy.M.dd hh:mm");
        String str=format.format(new Date());
        hv.tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclick.c2(hv.tv1.getText().toString(),hv.tv2.getText().toString(),hv.tv3.getText().toString(),hv.tv4.getText().toString(),str, xx.getSex());
                hv.hsv.scrollTo(0,0);
            }
        });
        hv.tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclick.c(position);
                hv.hsv.scrollTo(0,0);

            }
        });
        hv.hsv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View contentView = (View) hv.hsv.getChildAt(0);
                int contentWidth = contentView.getWidth();
                int hsvWidth = hv.hsv.getWidth();

                if (scrollX == 0) {
                    //已经滚动到左边界

                }

                if (scrollX + hsvWidth < contentWidth) {


                    //已经滚动到右边界
                    hv.hsv.scrollTo(0,0);
                }

            }
        });




        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
        ImageView iv;

        HorizontalScrollView hsv;

    }
}