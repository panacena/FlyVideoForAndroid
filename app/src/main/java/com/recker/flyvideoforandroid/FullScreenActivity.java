package com.recker.flyvideoforandroid;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by recker on 16/8/6.
 */
public class FullScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mVideoLayout;
    private VideoView mVideoView;
    private ImageView mIvIsFullScreen;

    private boolean mIsFullScreen = false;//是否为全屏

    private String mPlayUrl = "http://hd.yinyuetai.com/uploads/videos/common/" +
            "D777015139CEB3E600E048A98570437E.flv?sc=628a84be651d38bb";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
        setContentView(R.layout.activity_fullscreen);

        mVideoLayout = (FrameLayout) findViewById(R.id.video_layout);
        mVideoView = (VideoView) findViewById(R.id.videoview);
        mIvIsFullScreen = (ImageView) findViewById(R.id.iv_is_fullscreen);

        mIvIsFullScreen.setOnClickListener(this);

        init();
    }

    private void init() {
        //播放网络资源
        mVideoView.setVideoPath(mPlayUrl);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_is_fullscreen:
                if (mIsFullScreen) {
                    setupUnFullScreen();
                } else {
                    setupFullScreen();
                }
                break;
        }
    }

    /**
     * 设置为全屏
     */
    private void setupFullScreen() {
        //设置窗口模式
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //获取屏幕尺寸
        WindowManager manager = this.getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);

        //设置Video布局尺寸
        mVideoLayout.getLayoutParams().width = metrics.widthPixels;
        mVideoLayout.getLayoutParams().height = metrics.heightPixels;

        //设置为全屏拉伸
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        mIvIsFullScreen.setImageResource(R.drawable.not_fullscreen);

        mIsFullScreen = true;
    }

    /**
     * 设置为非全屏
     */
    private void setupUnFullScreen() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        float width = getResources().getDisplayMetrics().heightPixels;
        float height = dp2px(200.f);
        mVideoLayout.getLayoutParams().width = (int) width;
        mVideoLayout.getLayoutParams().height = (int) height;

        //设置为全屏
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        mIvIsFullScreen.setImageResource(R.drawable.play_fullscreen);

        mIsFullScreen = false;
    }

    @Override
    public void onBackPressed() {
        if (mIsFullScreen) {
            setupUnFullScreen();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * dp转px
     * @param dpValue
     * @return
     */
    private int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //如果还在播放，则暂停
        if (mVideoView.isPlaying())
            mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mVideoView.stopPlayback();
    }
}
