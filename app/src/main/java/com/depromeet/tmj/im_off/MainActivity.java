package com.depromeet.tmj.im_off;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.depromeet.tmj.im_off.features.main.TimerFragment;
import com.depromeet.tmj.im_off.features.main.VerticalViewPager;
import com.depromeet.tmj.im_off.features.main.ViewPagerAdapter;
import com.depromeet.tmj.im_off.service.alarm.NotificationAlarmManager;
import com.depromeet.tmj.im_off.utils.DateUtils;
import com.depromeet.tmj.im_off.utils.datastore.AppPreferencesDataStore;
import com.google.android.gms.ads.MobileAds;

import static com.depromeet.tmj.im_off.features.main.TimerFragment.REQUEST_PERMISSIONS;

public class MainActivity extends AppCompatActivity implements TimerFragment.ScrollCallback {
    private static final String EXTRA_IS_LEAVING = "EXTRA_IS_LEAVING";
    private VerticalViewPager viewPager;
    private boolean isLeaving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAdmob();
        initArgs();
        initNoti();
        initBinding();
        initViewPager();
    }

    @Override
    public void onClickStatistics() {
        viewPager.setCurrentItem(1);
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

    private void initAdmob() {
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }

    private void initArgs() {
        this.isLeaving = getIntent().getBooleanExtra(EXTRA_IS_LEAVING, false);
    }

    private void initBinding() {
        viewPager = findViewById(R.id.view_pager);
    }

    private void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), isLeaving);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    getWindow().setStatusBarColor(getResources()
                            .getColor(R.color.white, getTheme()));
                } else {
                    getWindow().setStatusBarColor(getResources()
                            .getColor(R.color.bg_statistics, getTheme()));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setScrollDurationFactor(2);
    }

    private void initNoti() {
        NotificationManager notifiyMgr = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notifiyMgr.cancelAll();

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

    public static Intent getCallingIntent(Context context, boolean isLeaving) {
        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra(EXTRA_IS_LEAVING, isLeaving);
        return intent;
    }
}
