package com.depromeet.tmj.im_off.features.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    }

    private void initUi() {

    }

    private void setStatisticsAdapter() {
        Injection.provideLeavingWorkRepository().getLeavingWorks(new LeavingWorkDataSource.LoadLeavingWorkCallaack() {
            @Override
            public void onDataLoaded(List<LeavingWork> leavingWorks) {
                if (rvStatistics != null) {
                    rvStatistics.setAdapter(new StatisticsAdapter(leavingWorks));
                    rvStatistics.scheduleLayoutAnimation();
                }
            }

            @Override
            public void onDataNotAvailable() {
                // TODO("통계 데이터 없을때 화면 설정 필요")
                Toast.makeText(getContext(), "통계 데이터 없음", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
