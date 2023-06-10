package com.example.tiku51_55.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Been.DB_ssjt;
import com.example.tiku51_55.utility_class.DBmarager;
import com.example.tiku51_55.utility_class.H;
import com.example.tiku51_55.R;
import com.example.tiku51_55.Been.SSJT;
import com.example.tiku51_55.Adapter.SSJT_Adapter_1;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity56 extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mMap;
    private EditText mEt;
    private TextView mSearch;
    private ListView mLv;
    private TextView mQk;
    private SSJT ssjt;
    List<DB_ssjt>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main56);
        initView();
        setDB();


    }
    public void setDB(){
        DBmarager dBmarager=new DBmarager(this);
        boolean b= dBmarager.isExist("ssjt");
        if(b==false){
            mLv.setVisibility(View.GONE);
        }else {
            Cursor c=dBmarager.queryDB("ssjt",null,null,null,null,null,null,null);
            list=dBmarager.sendees(c);
            SSJT_Adapter_1 ssjt_adapter_1=new SSJT_Adapter_1(this,list);
            mLv.setAdapter(ssjt_adapter_1);
            ssjt_adapter_1.setMyclick(new SSJT_Adapter_1.Myclick() {
                @Override
                public void c(int id) {
                    Intent intent=new Intent(MainActivity56.this, Bus_Route.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
        }
    }
    public void DelDB(){
        DBmarager dBmarager=new DBmarager(this);
        dBmarager.deltable("ssjt",null,null);
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mMap = (TextView) findViewById(R.id.map);
        mEt = (EditText) findViewById(R.id.et);
        mSearch = (TextView) findViewById(R.id.search);
        mLv = (ListView) findViewById(R.id.lv);
        mQk = (TextView) findViewById(R.id.qk);
        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity56.this, MainActivity51_52.class);
                startActivity(intent);
            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEt.getText().toString().equals("")){
                    getData(Integer.parseInt(mEt.getText().toString()));


                }else {
                    Toast.makeText(MainActivity56.this, "请输入线路", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mQk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DelDB();
                setDB();
                Toast.makeText(MainActivity56.this, "已清空记录", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData(int Busid){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("Busid",Busid);
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
                    ssjt=new Gson().fromJson(jsonObject1.toString(),SSJT.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(ssjt.getROWS_DETAIL().size()==0){
                                Toast.makeText(MainActivity56.this, "未查询到该车辆", Toast.LENGTH_SHORT).show();
                                mEt.setText("");
                            }else {
                                Intent intent=new Intent(MainActivity56.this,Bus_Route.class);
                                intent.putExtra("id",ssjt.getROWS_DETAIL().get(0).getId());
                                startActivity(intent);
                                DBmarager dBmarager=new DBmarager(MainActivity56.this);
                                boolean b=dBmarager.isExist("ssjt");
                                if(b==false){
                                    String sql = "create table ssjt (" +
                                            "id integer primary key autoincrement," +
                                            "                           number integer," +
                                            "                           xianlu varchar);";
                                    dBmarager.createtable(sql);
                                }
                                ContentValues cv=new ContentValues();
                                cv.put("number",ssjt.getROWS_DETAIL().get(0).getId());
                                cv.put("xianlu",ssjt.getROWS_DETAIL().get(0).getSite().get(0)+"-"+ssjt.getROWS_DETAIL().get(0).getSite().get(ssjt.getROWS_DETAIL().get(0).getSite().size()-1));
                                dBmarager.insertDB("ssjt",cv);

                            }
                            setDB();
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}