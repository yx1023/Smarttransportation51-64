package com.example.tiku51_55.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Adapter.GWC_Adapter;
import com.example.tiku51_55.Been.LX;
import com.example.tiku51_55.utility_class.Myc;
import com.example.tiku51_55.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GWC extends AppCompatActivity {

    private ImageView mMuns4;
    private TextView mJintian;
    private TextView mMingtian;
    private TextView mRiqi;

    private TextView mZong;
    private Button mLjzf;
    private Button mQkgwc;


    List<LX> list = new ArrayList<>();
    static List<LX> list1 = new ArrayList<>();

    private TextView mGuanli;
    int a = 1;
    private ListView mLvGwc;
    GWC_Adapter adapter;
    private int zong;

    static List<String> stringList = new ArrayList<>();

    public static List<String> getList() {
        return stringList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwc);
        initView();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        stringList.add(name);
        list = MainActivity63.getList();
        LX lx = new LX();
        lx = list.get(id);
        list1.add(lx);
        for (int i = 0; i < list1.size(); i++) {
            zong += Integer.parseInt(list1.get(i).getPrice());
        }
        mZong.setText(zong + "");
        adapter = new GWC_Adapter(this, list1);
        mLvGwc.setAdapter(adapter);
        adapter.setMyc(new Myc() {
            @Override
            public void c1(String i, int position) {
                System.out.println("++++++++++++" + position);
                if (i .equals("1")) {
                    int zong = Integer.parseInt(mZong.getText().toString());
                    mZong.setText(position + zong + "");
                } else if (i.equals("2")) {
                    int zong = Integer.parseInt(mZong.getText().toString());
                    mZong.setText(zong + position + "");
                } else if (i.equals("3")) {
                    System.out.println(position+"++++++++++++++");
                    list1.remove(position);
                    adapter = new GWC_Adapter(GWC.this, list1);
                    mLvGwc.setAdapter(adapter);
                    mZong.setText("0");
                }

            }
        });
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        String str = format.format(new Date());
        mJintian.setText("今天" + str + "预定");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String tomorrowDate = new SimpleDateFormat("MM/dd").format(tomorrow);
        mMingtian.setText("明天" + tomorrowDate + "预定");
    }

    private void initView() {
        mMuns4 = (ImageView) findViewById(R.id.muns4);
        mJintian = (TextView) findViewById(R.id.jintian);
        mMingtian = (TextView) findViewById(R.id.mingtian);
        mRiqi = (TextView) findViewById(R.id.riqi);
        mZong = (TextView) findViewById(R.id.zong);
        mLjzf = (Button) findViewById(R.id.ljzf);
        mQkgwc = (Button) findViewById(R.id.qkgwc);
        mLvGwc = (ListView) findViewById(R.id.lv_gwc);

        mGuanli = (TextView) findViewById(R.id.guanli);
        mMuns4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        mQkgwc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list1.clear();
                adapter = new GWC_Adapter(GWC.this, list1);
                mLvGwc.setAdapter(adapter);
                mZong.setText("");
            }
        });
        mMingtian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMingtian.setTextColor(Color.BLACK);
                mJintian.setTextColor(Color.GRAY);
            }
        });
        mJintian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMingtian.setTextColor(Color.GRAY);
                mJintian.setTextColor(Color.BLACK);
            }
        });
        mLjzf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GWC.this, LJZF.class);
                intent.putExtra("money", mZong.getText().toString());
                startActivity(intent);
            }
        });
        mGuanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (list1.get(0).isB() == true) {
                    for (int i = 0; i < list1.size(); i++) {
                        list1.get(i).setB(false);
                    }

                } else {
                    for (int i = 0; i < list1.size(); i++) {
                        list1.get(i).setB(true);
                    }

                }
                 adapter.notifyDataSetChanged();

//                for (int i = 0; i < list1.size(); i++) {
//                    list1.get(i).setB(true);
//                }
//                if (is) {
//                    for (int i = 0; i < list1.size(); i++) {
//                        list1.get(i).setB(true);
//                    }
//                    adapter = new GWC_Adapter(GWC.this, list1);
//                    mLvGwc.setAdapter(adapter);
//                    is = false;
//                } else {
//                    for (int i = 0; i < list1.size(); i++) {
//                        list1.get(i).setB(false);
//                    }
//                    adapter = new GWC_Adapter(GWC.this, list1);
//                    mLvGwc.setAdapter(adapter);
//                    is = true;
//                }
            }
        });
        mRiqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // 创建DatePickerDialog对象
                DatePickerDialog datePickerDialog = new DatePickerDialog(GWC.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // 设置选择的日期为TextView的文本
//                                dateTextView.setText(String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth));
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });
    }

    private boolean is = true;
}