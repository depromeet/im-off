package com.depromeet.tmj.im_off;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.depromeet.tmj.im_off.features.main.TimerFragment;
import com.depromeet.tmj.im_off.features.main.VerticalViewPager;
import com.depromeet.tmj.im_off.features.main.ViewPagerAdapter;
import com.depromeet.tmj.im_off.service.alarm.NotificationAlarmManager;

import static com.depromeet.tmj.im_off.features.main.TimerFragment.REQUEST_PERMISSIONS;

public class MainActivity extends AppCompatActivity implements TimerFragment.ScrollCallback {

    private VerticalViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNoti();
        initBinding();
        initViewPager();
    }

    @Override
    public void onClickStatistics() {
        viewPager.setCurrentItem(1, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS
                && permissions[0].equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setShare();
        }
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

    private void setShare() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof TimerFragment) {
                ((TimerFragment) fragment).setShare();
            }
        }
    }
}
