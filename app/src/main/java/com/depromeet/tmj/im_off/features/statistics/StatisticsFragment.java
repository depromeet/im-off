package com.depromeet.tmj.im_off.features.statistics;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.depromeet.tmj.im_off.R;


public class StatisticsFragment extends Fragment {
    private static final String TAG = "Statistics";

    private RecyclerView rvStatistics;

    public StatisticsFragment() {
    }

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        initBinding(view);
        initUi();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            setStatisticsAdapter();
        }
    }

    private void initBinding(View view) {
        rvStatistics = view.findViewById(R.id.rv_statistics);
    }

    private void initUi() {

    }

    private void setStatisticsAdapter() {
        if(rvStatistics != null) {
            rvStatistics.setAdapter(new StatisticsAdapter());
            rvStatistics.scheduleLayoutAnimation();
        }
    }
}
