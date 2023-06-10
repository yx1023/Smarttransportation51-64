package com.example.tiku51_55.utility_class;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ImageListener implements View.OnTouchListener {
    private ImageView imageView;
    private Matrix matrix=new Matrix();
    private Matrix current=new Matrix();
    private PointF start=new PointF();
    private float dis;
    private int NONE=9;
    private int mode=NONE,ZOOM=1,DROP=5;
    private float lastScale;


    public ImageListener(ImageView image) {
        this.imageView = image;
        matrix.set(imageView.getImageMatrix());
        current.set(imageView.getImageMatrix());
    }
    private float distance(MotionEvent event){
        float dx=event.getX(1)-event.getX(0);
        float dy=event.getY(1)-event.getY(0);
        return (float) Math.sqrt(dx*dx+dy*dy);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                mode=DROP;
                matrix.set(imageView.getImageMatrix());
                current.set(imageView.getImageMatrix());
                start.set(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode==DROP){
                    float dx=event.getX()-start.x;
                    float dy=event.getY()-start.y;
                    matrix.set(current);
                    matrix.postTranslate(dx,dy);
                }else if (mode==ZOOM){
                    float end=distance(event);
                    if (end>10f){
                        float scale=lastScale-1+end/dis;
                        if (scale<=1){
                            scale=1;
                        }
                        imageView.setScaleX(scale);
                        imageView.setScaleY(scale);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode=0;
                break;
            case MotionEvent.ACTION_UP:
                mode=NONE;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode=ZOOM;
                dis=distance(event);
                if (dis>10){
                    current.set(imageView.getImageMatrix());
                    lastScale=imageView.getScaleX();
                }
                break;
        }
        imageView.setImageMatrix(matrix);
        return true;

    }
}
