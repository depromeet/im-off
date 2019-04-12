package com.depromeet.tmj.im_off;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.depromeet.tmj.im_off.features.main.TimerFragment;
import com.depromeet.tmj.im_off.features.main.VerticalViewPager;
import com.depromeet.tmj.im_off.features.main.ViewPagerAdapter;
import com.depromeet.tmj.im_off.service.alarm.NotificationAlarmManager;

public class MainActivity extends AppCompatActivity implements TimerFragment.ScrollCallback {

    private VerticalViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);

        initNoti();
        initBinding();
        initViewPager();
    }

    @Override
    public void onClickStatistics() {
        viewPager.setCurrentItem(1, true);
    }

    private void initBinding() {
        viewPager = findViewById(R.id.view_pager);
    }

    private void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void initNoti() {
        NotificationAlarmManager alarmManager = new NotificationAlarmManager(this);
        alarmManager.registerAll();
    }
}
