package com.example.tiku51_55.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku51_55.R;

public class Video extends AppCompatActivity {

    private VideoView mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
        Intent intent=getIntent();
        String uri=intent.getStringExtra("uri");
        playVideo(uri);


    }

    private void initView() {
        mVideo = (VideoView) findViewById(R.id.video);
    }

    public void playVideo(String uri){
        MediaController mediaController=new MediaController(this);
        mVideo.setVideoURI(Uri.parse(uri));
        mVideo.setMediaController(mediaController);
        mediaController.setMediaPlayer(mVideo);
        mediaController.setAnchorView(mVideo);

        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                mVideo.stopPlayback();
                finish();
            }
        });
        mVideo.start();
    }
}