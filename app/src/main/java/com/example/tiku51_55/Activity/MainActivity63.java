package com.example.tiku51_55.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Adapter.LX_Adapter;
import com.example.tiku51_55.Been.LX;
import com.example.tiku51_55.R;
import com.example.tiku51_55.utility_class.H;
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

public class MainActivity63 extends AppCompatActivity {

    private ImageView mMuns4;
    private GridView mGvLx;
    static List<LX> list = new ArrayList<>();
    LX_Adapter adapter;

    public static List<LX> getList() {

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main63);
        initView();
        getData();
    }

    private void initView() {
        mMuns4 = (ImageView) findViewById(R.id.muns4);
        mGvLx = (GridView) findViewById(R.id.gv_lx);
    }

    public void getData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_scenic_spot", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    list = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<LX>>() {
                            }.getType());


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new LX_Adapter(MainActivity63.this, list);
                            mGvLx.setAdapter(adapter);
                            adapter.setMyClik(new LX_Adapter.MyClik() {
                                @Override
                                public void clik(int id) {
                                    Intent intent = new Intent(getApplicationContext(), XXXX.class);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                }

                                @Override
                                public void clike(int id, String s) {
                                    Intent intent = new Intent(MainActivity63.this, GWC.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("name", s);
                                    startActivity(intent);
                                }
                            });
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}