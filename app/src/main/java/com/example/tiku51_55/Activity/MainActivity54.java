package com.example.tiku51_55.Activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.utility_class.DBmarager;
import com.example.tiku51_55.utility_class.H;
import com.example.tiku51_55.Adapter.MyAdapter;
import com.example.tiku51_55.R;
import com.example.tiku51_55.Been.XX;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity54 extends AppCompatActivity {

    private ImageView mFanhui;
    private ListView mRV;
    List<XX>list=new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main54);
        initView();
        getData();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mRV = (ListView) findViewById(R.id.RV);
    }

    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResuilt("get_roots", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<XX>>(){}.getType());

                    list.sort(new Comparator<XX>() {
                        @Override
                        public int compare(XX o1, XX o2) {
                            return o1.getId()-o2.getId();
                        }
                    });

                    System.out.println(list.size()+"/////////////////");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            adapter=new MyAdapter(MainActivity54.this,list);
                            mRV.setAdapter(adapter);
                            adapter.setMyclick(new MyAdapter.Myclick() {
                                @Override
                                public void c(int position) {
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void c2(String username, String name, String tel, String status,String time,String sex) {
                                    System.out.println(username+"   "+name+"    "+tel+"   "+status+"  "+time);
                                    DBmarager dBmarager = new DBmarager(MainActivity54.this);
                                    boolean result = dBmarager.isExist("xx");
                                    if (result == false) {
                                        String sql = "create table xx (" +
                                                "id integer primary key autoincrement," +
                                                "                           username varchar," +
                                                "                           name varchar," +
                                                "                           tel varchar," +
                                                "                           time varchar," +
                                                "                           sex varchar," +
                                                "                           status varchar);";
                                        dBmarager.createtable(sql);
                                    }
                                    ContentValues cv = new ContentValues();
                                    cv.put("username", username);
                                    cv.put("name", name);
                                    cv.put("tel", tel);
                                    cv.put("time",time);
                                    cv.put("status",status);
                                    cv.put("sex",sex);
                                    dBmarager.insertDB("xx", cv);
                                    Toast.makeText(MainActivity54.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                }

                            });

//                            ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//                                @Override
//                                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                                    return false;
//                                }
//
//
//                                @Override
//                                public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//
//                                    int swipeFlags = ItemTouchHelper.LEFT;   //只允许从右向左侧滑
//                                    return makeMovementFlags(0,swipeFlags);
//                                }
//
//                                @Override
//                                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//                                    View itemView = viewHolder.itemView;
//
//                                    if (dX > 0) {
//                                        int deleteButtonWidth = viewHolder.itemView.getHeight();
//                                        itemView.findViewById(R.id.shanchu).setVisibility(View.VISIBLE);
//                                        itemView.findViewById(R.id.shanchu).setTranslationX(-deleteButtonWidth);
//                                        itemView.setTranslationX(dX - deleteButtonWidth);
//                                    } else {
//                                        itemView.findViewById(R.id.shanchu).setVisibility(View.INVISIBLE);
//                                        itemView.setTranslationX(dX);
//                                    }
//                                }
//
//                                @Override
//                                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                                    int position = viewHolder.getAdapterPosition();
//                                    adapter.removeItem(position);
//                                }
//                            };
//
//
//
//                            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//                            touchHelper.attachToRecyclerView(mRV);
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}