package com.depromeet.tmj.im_off.features.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.data.LeavingWork;
import com.depromeet.tmj.im_off.data.source.LeavingWorkDataSource;
import com.depromeet.tmj.im_off.utils.Injection;

import java.util.List;


public class StatisticsFragment extends Fragment {
    private static final String TAG = "Statistics";

    private RecyclerView rvStatistics;
    private TextView tvNoData;

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
        if (isVisibleToUser) {
            setStatisticsAdapter();
        }
    }

    private void initBinding(View view) {
        rvStatistics = view.findViewById(R.id.rv_statistics);
        tvNoData = view.findViewById(R.id.tv_no_data);
    }

    private void initUi() {

    }

    private void setStatisticsAdapter() {
        Injection.provideLeavingWorkRepository().getLeavingWorks(new LeavingWorkDataSource.LoadLeavingWorkCallaack() {
            @Override
            public void onDataLoaded(List<LeavingWork> leavingWorks) {
                tvNoData.setVisibility(View.GONE);
                rvStatistics.setVisibility(View.VISIBLE);
                if (rvStatistics != null) {
                    rvStatistics.setAdapter(new StatisticsAdapter(leavingWorks));
                    rvStatistics.scheduleLayoutAnimation();
                }
            }

            @Override
            public void onDataNotAvailable() {
                tvNoData.setVisibility(View.VISIBLE);
                rvStatistics.setVisibility(View.INVISIBLE);
            }
        });
    }
}
