package com.depromeet.tmj.im_off.features.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.SettingActivity;
import com.depromeet.tmj.im_off.shared.RoundProgressBar;


public class TimerFragment extends Fragment {
    private RoundProgressBar roundProgressBar;
    private ImageButton btnSetting;
    private TextView tvStatistics;
    private ScrollCallback scrollCallBack;

    public TimerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ScrollCallback) {
            scrollCallBack = (ScrollCallback) context;
        } else {
            throw new RuntimeException("ScrollCallback is not implemented");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        initBinding(view);
        initUi();
        return view;
    }

    private void initBinding(View view) {
        roundProgressBar = view.findViewById(R.id.round_progress);
        btnSetting = view.findViewById(R.id.btn_setting);
        tvStatistics = view.findViewById(R.id.tv_statistics);
    }

    private void initUi() {
        roundProgressBar.setText("09:00");
        roundProgressBar.setProgress(30);

        btnSetting.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);
        });

        tvStatistics.setOnClickListener(view -> {
            scrollCallBack.onClickStatistics();
        });
    }

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    public interface ScrollCallback {
        void onClickStatistics();
    }
}
