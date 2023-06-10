package com.example.tiku51_55.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.utility_class.ImageListener;
import com.example.tiku51_55.R;
import com.example.tiku51_55.Been.Three;

import java.util.List;

public class CXJG extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mRoad;
    private TextView mMessage;
    private ImageView mZuo;
    private ImageView mPhoto;
    private VideoView mVideo;
    private ImageView mYou;
    private TextView mWz;
    private TextView mFk;
    private TextView mKf;
    List<Three>list= MainActivity59.getList();
    private int temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cxjg);
        initView();


        setInfo(temp);
        mPhoto.setOnTouchListener(new ImageListener(mPhoto));


    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mRoad = (TextView) findViewById(R.id.road);
        mMessage = (TextView) findViewById(R.id.message);
        mZuo = (ImageView) findViewById(R.id.zuo);
        mPhoto = (ImageView) findViewById(R.id.photo);
        mVideo = (VideoView) findViewById(R.id.video);
        mYou = (ImageView) findViewById(R.id.you);
        mWz = (TextView) findViewById(R.id.wz);
        mFk = (TextView) findViewById(R.id.fk);
        mKf = (TextView) findViewById(R.id.kf);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mZuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp==0){
                    Toast.makeText(CXJG.this, "这是第一条违章记录了", Toast.LENGTH_SHORT).show();
                }else {
                    setInfo(temp-1);
                    temp-=1;
                }
            }
        });
        mYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp==(list.size()-1)){
                    Toast.makeText(CXJG.this, "这是最后一条违章记录了", Toast.LENGTH_SHORT).show();
                }else {
                    setInfo(temp+1);
                    temp+=1;
                }
            }
        });
        mVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "android.resource://" + getPackageName() + "/" + R.raw.car1;
                Intent intent=new Intent(CXJG.this, Video.class);
                intent.putExtra("uri",uri);
                startActivity(intent);

            }
        });
    }

    public void setInfo(int temp){
        int deducts=0;
        int fines=0;
        if(list.size()==1){
            mZuo.setVisibility(View.INVISIBLE);
            mYou.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < list.size(); i++) {
            int deduct=list.get(i).getDeduct();
            int fine=list.get(i).getFine();
            deducts+=deduct;
            fines+=fine;
        }
        mFk.setText(fines+"");
        mKf.setText(deducts+"");


        mWz.setText(list.size()+"");
        mMessage.setText(list.get(temp).getMessage());
        mRoad.setText(list.get(temp).getRoad());



    }


}