package com.example.tiku51_55.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.utility_class.H;
import com.example.tiku51_55.Been.One;
import com.example.tiku51_55.R;
import com.example.tiku51_55.Been.Three;
import com.example.tiku51_55.Been.Two;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity59 extends AppCompatActivity {

    private ImageView mFanhui;
    private EditText mEt;
    private Button mBt;
    private List<Two> list_Two=new ArrayList<>();
    private List<String>list=new ArrayList<>();
    private List<Three>list_three=new ArrayList<>();
    private static List<Three>threeList=new ArrayList<>();
    private One one=new One();

    public static List<Three> getList() {
        return threeList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main59);
        initView();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mEt = (EditText) findViewById(R.id.et);
        mBt = (Button) findViewById(R.id.bt);
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlate();
            }
        });
    }
    public void getPlate(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate","鲁"+mEt.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_peccancy_plate", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    one = new Gson().fromJson(jsonObject1.toString(),One.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(one.getId().size()==0){
                                Toast.makeText(MainActivity59.this, "没有查询到鲁"+mEt.getText().toString()+"车的违章记录", Toast.LENGTH_SHORT).show();
                            }else {
                                    getAll();
                            }
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getAll(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_all_car_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list_Two=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Two>>(){}.getType());

                    for (int i = 0; i < list_Two.size(); i++) {
                        for (int j = 0; j < one.getId().size(); j++) {
                            if(one.getId().get(j)==list_Two.get(i).getId()){
                               list.add(list_Two.get(i).getInfoid());
                            }
                        }
                    }

                    getInfo();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getInfo(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResuilt("get_pessancy_infos", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list_three=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Three>>(){}.getType());

                    for (int i = 0; i < list_three.size(); i++) {
                        for (int j = 0; j < list.size(); j++) {
                            if(list_three.get(i).getInfoid().equals(list.get(j))){
                                threeList.add(list_three.get(i));
                            }
                        }
                    }
                    System.out.println(threeList.size()+"*********");

                    Intent intent=new Intent(MainActivity59.this, CXJG.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}