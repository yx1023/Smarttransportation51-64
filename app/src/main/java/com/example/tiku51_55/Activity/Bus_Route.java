package com.example.tiku51_55.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.utility_class.H;
import com.example.tiku51_55.R;
import com.example.tiku51_55.Been.SSJT;
import com.example.tiku51_55.Adapter.SSJT_Adapter_2;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Bus_Route extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mRoute;
    private TextView mXianlu;
    private TextView mSTime;
    private TextView mZTime;
    private TextView mTvQc;
    private TextView mTvJz;
    private ListView mLv;
    SSJT_Adapter_2 adapter_2;
    private SSJT ssjt=new SSJT();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route);
        initView();
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
        System.out.println(id+"//////////////");
        getData(id);

    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mRoute = (TextView) findViewById(R.id.route);
        mXianlu = (TextView) findViewById(R.id.xianlu);
        mSTime = (TextView) findViewById(R.id.sTime);
        mZTime = (TextView) findViewById(R.id.zTime);
        mTvQc = (TextView) findViewById(R.id.tv_qc);
        mTvJz = (TextView) findViewById(R.id.tv_jz);
        mLv = (ListView) findViewById(R.id.lv);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter_2.setLastClickedPosition(position);
                adapter_2.notifyDataSetChanged();
            }
        });
    }
    public void getData(int id){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("Busid",id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_bus_data", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    ssjt =new Gson().fromJson(jsonObject1.toString(),SSJT.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter_2=new SSJT_Adapter_2(Bus_Route.this,ssjt.getROWS_DETAIL().get(0).getSite());
                            mLv.setAdapter(adapter_2);
                            mRoute.setText(id+"路");
                            mSTime.setText(ssjt.getROWS_DETAIL().get(0).getStart());
                            mZTime.setText(ssjt.getROWS_DETAIL().get(0).getEnd());
                            mXianlu.setText(ssjt.getROWS_DETAIL().get(0).getSite().get(0)+"-"+ssjt.getROWS_DETAIL().get(0).getSite().get(ssjt.getROWS_DETAIL().get(0).getSite().size()-1));
                            mTvQc.setText(ssjt.getROWS_DETAIL().get(0).getSite().size()+"站/"+ssjt.getROWS_DETAIL().get(0).getMileage()+"公里");
                            mTvJz.setText("最高票价"+ssjt.getROWS_DETAIL().get(0).getPrice()+"元");
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}