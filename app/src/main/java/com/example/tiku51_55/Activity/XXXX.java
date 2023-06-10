package com.example.tiku51_55.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Been.LX;
import com.example.tiku51_55.R;

import java.util.List;

public class XXXX extends AppCompatActivity {

    private ImageView mFanhui2;
    private ImageView mIV;
    private TextView mJs;
    private RatingBar mRatingBar;
    private TextView mTel;
    List<LX>list= MainActivity63.getList();
    private LX lx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xxxx);
        initView();

        lx=new LX();
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
        lx=list.get(id);
        if(lx.getName().equals("故宫")){
            mIV.setImageResource(R.drawable.gugong1);
        }else {
            mIV.setImageResource(R.drawable.tiananmen);
        }
        mRatingBar.setRating(Float.parseFloat(lx.getGrade()));
        mJs.setText(lx.getPresentation());
        mTel.setText(lx.getTel());

    }

    private void initView() {
        mFanhui2 = (ImageView) findViewById(R.id.fanhui2);
        mIV = (ImageView) findViewById(R.id.IV);
        mJs = (TextView) findViewById(R.id.js);
        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mTel = (TextView) findViewById(R.id.tel);
        mFanhui2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + lx.getTel()));
                startActivity(intent);
            }
        });
    }
}