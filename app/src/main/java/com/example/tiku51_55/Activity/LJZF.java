package com.example.tiku51_55.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.Activity.GWC;
import com.example.tiku51_55.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LJZF extends AppCompatActivity {

    private ImageView mMuns4;
    private TextView mXinxi;
    private ImageView mIv1;
    List<String>list= GWC.getList();
    private String finalDz;
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ljzf);
        initView();
        Intent intent=getIntent();
        money = intent.getStringExtra("money");
        String dz = "";
        for (int i = 0; i < list.size(); i++) {
            dz+=list.get(i);
        }
        finalDz = dz;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Random random=new Random();
                        setEWM("付款项目：" + finalDz + ",付费金额=" + money + "元"+random.nextInt(100));
                    }
                },0,5000);
            }
        });
    }


    private void initView() {
        mMuns4 = (ImageView) findViewById(R.id.muns4);
        mXinxi = (TextView) findViewById(R.id.xinxi);
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mMuns4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mXinxi.setText("付款项目：" + finalDz + ",付费金额="+ money + "元");
                return false;
            }
        });

    }

    public void setEWM(String uri) {
        Hashtable<EncodeHintType, String> hashtable = new Hashtable<>();
        hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(uri, BarcodeFormat.QR_CODE, 300, 300, hashtable);
            int[] pix = new int[300 * 300];
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    if (bitMatrix.get(x, y)) {
                        pix[y * 300 + x] = 0xff000000;
                    } else {
                        pix[y * 300 + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pix, 0, 300, 0, 0, 300, 300);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mIv1.setImageBitmap(bitmap);
                }
            });

        }  catch (WriterException e) {
            throw new RuntimeException(e);
        }

    }
}