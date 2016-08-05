package com.recker.flyvideoforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTvFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvFullScreen = (TextView) findViewById(R.id.tv_fullscreen);

        mToolbar.setTitle(getResources().getString(R.string.app_name));

        mTvFullScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fullscreen:
                Intent intent = new Intent(MainActivity.this, FullScreenActivity.class);
                startActivity(intent);
                break;
        }
    }
}
