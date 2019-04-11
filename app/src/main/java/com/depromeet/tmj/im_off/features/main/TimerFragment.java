package com.depromeet.tmj.im_off.features.main;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.shared.RoundProgressBar;


public class TimerFragment extends Fragment {
    private RoundProgressBar roundProgressBar;

    public TimerFragment() {
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
    }

    private void initUi() {
        roundProgressBar.setText("09:00");
        roundProgressBar.setProgress(30);
    }

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

}
