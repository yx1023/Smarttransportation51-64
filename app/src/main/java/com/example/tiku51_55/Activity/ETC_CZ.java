package com.example.tiku51_55.Activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.R;
import com.example.tiku51_55.utility_class.DBmarager;
import com.example.tiku51_55.utility_class.H;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ETC_CZ extends AppCompatActivity {

    private ImageView mFanhui;
    private EditText mEtNumber;
    private TextView mPlate;
    private TextView mTen;
    private TextView mFifty;
    private TextView mHundred;
    private EditText mRtMoney;
    private Button mBt;
    private String plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_cz);
        initView();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mEtNumber = (EditText) findViewById(R.id.et_number);
        mPlate = (TextView) findViewById(R.id.plate);
        mTen = (TextView) findViewById(R.id.ten);
        mFifty = (TextView) findViewById(R.id.fifty);
        mHundred = (TextView) findViewById(R.id.hundred);
        mRtMoney = (EditText) findViewById(R.id.rt_money);
        mBt = (Button) findViewById(R.id.bt);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTen.setBackgroundResource(R.drawable.bgs);
                mFifty.setBackgroundResource(R.drawable.bg);
                mHundred.setBackgroundResource(R.drawable.bg);
                mRtMoney.setText(10+"");
            }
        });
        mFifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTen.setBackgroundResource(R.drawable.bg);
                mFifty.setBackgroundResource(R.drawable.bgs);
                mHundred.setBackgroundResource(R.drawable.bg);
                mRtMoney.setText(50+"");
            }
        });
        mHundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTen.setBackgroundResource(R.drawable.bg);
                mFifty.setBackgroundResource(R.drawable.bg);
                mHundred.setBackgroundResource(R.drawable.bgs);
                mRtMoney.setText(100+"");
            }
        });
        mEtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mEtNumber.getText().toString().equals("")){
                    int number= Integer.parseInt(mEtNumber.getText().toString());
                    getPlate(number);
                }else {
                    mPlate.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlate.getText().toString().equals("")){
                    Toast.makeText(ETC_CZ.this, "请输入正确的车辆编号来查询车牌号", Toast.LENGTH_SHORT).show();
                }else {
                    if(mRtMoney.getText().toString().equals("")){
                        Toast.makeText(ETC_CZ.this, "请输入充值金额", Toast.LENGTH_SHORT).show();
                    }else {
                        String plate=mPlate.getText().toString();
                        int money= Integer.parseInt(mRtMoney.getText().toString());
                        CZ(plate,money);
                    }
                }
            }
        });
    }


    public void getPlate(int number){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",number);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResuilt("get_plate", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    plate = jsonObject1.getString("plate");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!plate.equals("")){
                                mPlate.setText(plate);
                            }
                        }
                    });

                } catch (JSONException e) {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           mPlate.setText("");
                       }
                   });
                }
            }
        });
    }
    public void CZ(String plate,int money){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate",plate);
            jsonObject.put("balance",money);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResuilt("set_balance", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    String RESULT=jsonObject1.getString("RESULT");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(RESULT.equals("S")){
                                Toast.makeText(ETC_CZ.this, "充值成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ETC_CZ.this, "充值失败", Toast.LENGTH_SHORT).show();
                            }
                            SimpleDateFormat format2=new SimpleDateFormat("yyyy.MM.dd hh:mm");
                            String time2=format2.format(new Date());
                            SimpleDateFormat format1=new SimpleDateFormat("yyyy.MM.dd");
                            String time1=format1.format(new Date());

                            setSQL(time1,"admin",plate,time2,getWeek(), String.valueOf(money));


                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setSQL(String time1,String name,String plate,String time2,String week,String money){
        DBmarager dBmarager=new DBmarager(this);
        boolean b= dBmarager.isExist("ETC");
        if(b==false){
            String sql = "create table ETC (" +
                    "id integer primary key autoincrement," +
                    "                           time1 varchar," +
                    "                           name varchar," +
                    "                           plate varchar," +
                    "                           time2 varchar," +
                    "                           week varchar," +
                    "                           money varchar);";
            dBmarager.createtable(sql);
        }
        ContentValues cv = new ContentValues();
        cv.put("time1", time1);
        cv.put("name", name);
        cv.put("plate", plate);
        cv.put("time2",time2);
        cv.put("week",week);
        cv.put("money",money);
        dBmarager.insertDB("ETC", cv);
    }

    public String getWeek(){
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String weekDayStr;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                weekDayStr = "星期日";
                break;
            case Calendar.MONDAY:
                weekDayStr = "星期一";
                break;
            case Calendar.TUESDAY:
                weekDayStr = "星期二";
                break;
            case Calendar.WEDNESDAY:
                weekDayStr = "星期三";
                break;
            case Calendar.THURSDAY:
                weekDayStr = "星期四";
                break;
            case Calendar.FRIDAY:
                weekDayStr = "星期五";
                break;
            case Calendar.SATURDAY:
                weekDayStr = "星期六";
                break;
            default:
                weekDayStr = "";
                break;
        }
        return weekDayStr;

    }
}