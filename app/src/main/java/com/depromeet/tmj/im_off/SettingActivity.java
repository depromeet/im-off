package com.depromeet.tmj.im_off;


import android.os.Bundle;
import android.widget.TextView;

import com.depromeet.tmj.im_off.shared.BaseActivity;

public class SettingActivity extends BaseActivity {
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initBinding();
    }

    private void initBinding() {
        tvTitle = findViewById(R.id.tv_title);
    }
}
