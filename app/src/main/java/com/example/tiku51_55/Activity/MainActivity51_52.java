package com.example.tiku51_55.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.tiku51_55.R;

public class MainActivity51_52 extends AppCompatActivity {

    private MapView mMap;
    private AMap aMap;
    private ImageView mFanhui;
    private ImageView mIv1;
    private ImageView mIv2;
    private ImageView mIv3;
    private ImageView mIv4;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main51_52);
        initView();


        //隐私权限
        MapsInitializer.updatePrivacyShow(this, true, true);
        MapsInitializer.updatePrivacyAgree(this, true);
        mMap.onCreate(savedInstanceState);
        aMap = mMap.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);


    }

    private void initView() {
        mMap = (MapView) findViewById(R.id.map);
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mIv2 = (ImageView) findViewById(R.id.iv2);
        mIv3 = (ImageView) findViewById(R.id.iv3);
        mIv4 = (ImageView) findViewById(R.id.iv4);
        mText = (TextView) findViewById(R.id.text);
        mIv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AMap aMap = mMap.getMap();
                // 确定经纬度
                LatLng latLng = new LatLng(39.948881, 116.393923);
                LatLng latLng2 = new LatLng(39.945443, 116.400339);
                LatLng latLng3 = new LatLng(39.925469, 116.396799);
                LatLng latLng4 = new LatLng(39.916253, 116.352682);

                // 创建标记对象
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng).title("1号小车").snippet("鼓楼大街").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_one));

                MarkerOptions markerOptions2 = new MarkerOptions();
                markerOptions2.position(latLng2).title("2号小车").snippet("北京成林医院").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_second));

                MarkerOptions markerOptions3 = new MarkerOptions();
                markerOptions3.position(latLng3).title("3号小车").snippet("景山公园").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_thrid));

                MarkerOptions markerOptions4 = new MarkerOptions();
                markerOptions4.position(latLng4).title("4号小车").snippet("月坛公园").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_forth));

                // 将标记添加到地图上
                aMap.addMarker(markerOptions);
                aMap.addMarker(markerOptions2);
                aMap.addMarker(markerOptions3);
                aMap.addMarker(markerOptions4);

                mText.setText("1,2,3,4号小车地图标记已完成");



            }
        });
        mIv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(MainActivity51_52.this, mIv2);
                menu.inflate(R.menu.menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.DH:
                                aMap.setMapType(AMap.MAP_TYPE_NAVI);
                                break;
                            case R.id.YJ:
                                aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                                break;
                            case R.id.BZ:
                                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                                break;
                            case R.id.WX:
                                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                                break;
                            case R.id.JT:
                                aMap.setMapType(AMap.MAP_TYPE_BUS);
                                break;
                        }


                        return false;
                    }
                });
            }
        });
    }


}