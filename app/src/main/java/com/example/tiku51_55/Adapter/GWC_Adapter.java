package com.example.tiku51_55.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku51_55.Been.LX;
import com.example.tiku51_55.utility_class.Myc;
import com.example.tiku51_55.R;

import java.util.List;

public class GWC_Adapter extends ArrayAdapter<LX> {
    public GWC_Adapter(@NonNull Context context, List<LX> resource) {
        super(context, 0, resource);
    }
    public Myc myc;

    public void setMyc(Myc myc) {
        this.myc = myc;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gwc_item, parent, false);
            hv = new HV();
            hv.iv = convertView.findViewById(R.id.iv_gg);
            hv.jia = convertView.findViewById(R.id.jia);
            hv.jian = convertView.findViewById(R.id.jian);
            hv.shu = convertView.findViewById(R.id.shu);
            hv.name = convertView.findViewById(R.id.name);
            hv.shanchu = convertView.findViewById(R.id.shanchu);
            hv.jiage = convertView.findViewById(R.id.jiage);
            hv.jj = convertView.findViewById(R.id.jj);
            convertView.setTag(hv);
        } else {
            hv = (HV) convertView.getTag();
        }
        LX lx = getItem(position);
        hv.name.setText(lx.getName());
        if (lx.getName().equals("故宫")) {
            hv.iv.setImageResource(R.drawable.gugong1);
        } else {
            hv.iv.setImageResource(R.drawable.tiananmen);
        }
        hv.jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(hv.shu.getText().toString());
                hv.shu.setText(number + 1 + "");

                myc.c1("1", (Integer.parseInt(lx.getPrice())));

            }
        });
        hv.jj.setText(lx.getPresentation());

        hv.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(hv.shu.getText().toString());
                hv.shu.setText(number - 1 + "");
                int num = Integer.parseInt(hv.shu.getText().toString());
                if (num == 0) {
                    hv.jian.setEnabled(false);
                    hv.jian.setTextColor(Color.GRAY);
                }
                if (num != 0) {
                    hv.jian.setEnabled(true);
                    hv.jian.setTextColor(Color.BLACK);
                }

                myc.c1("2", (-Integer.parseInt(lx.getPrice())));
            }
        });



        hv.jiage.setText(lx.getPrice() + "");


        if (lx.isB()) {
            hv.shanchu.setVisibility(View.INVISIBLE);
        } else {
            hv.shanchu.setVisibility(View.VISIBLE);
        }

        hv.shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position+"------------");

                myc.c1("3", position);
            }
        });

        if (lx.getI() == null) {
            hv.shu.setText("1");
        } else {
            hv.shu.setText(lx.getI() + "");
        }


        return convertView;
    }

    static class HV {
        TextView jj;
        TextView shanchu;
        TextView jia;
        TextView jian;
        TextView shu;
        TextView jiage;
        TextView name;
        ImageView iv;
    }
}
