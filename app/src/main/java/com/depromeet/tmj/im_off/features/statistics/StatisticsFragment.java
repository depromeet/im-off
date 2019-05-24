package com.depromeet.tmj.im_off.features.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.depromeet.tmj.im_off.BuildConfig;
import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.data.LeavingWork;
import com.depromeet.tmj.im_off.data.source.LeavingWorkDataSource;
import com.depromeet.tmj.im_off.utils.Injection;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;


public class StatisticsFragment extends Fragment {
    private static final String TAG = "Statistics";
    private static final int ADVIEW_ID = 100;

    private RecyclerView rvStatistics;
    private TextView tvNoData;
    private ConstraintLayout root;

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
        initAdmob();
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
        root = view.findViewById(R.id.root);
    }

    private void initUi() {

    }

    private void initAdmob() {
        AdView adView = new AdView(getContext());

        adView.setId(ADVIEW_ID);
        adView.setLayoutParams(new AdView.LayoutParams(AdView.LayoutParams.MATCH_PARENT, AdView.LayoutParams.WRAP_CONTENT));
        adView.setAdSize(AdSize.SMART_BANNER);
        if (BuildConfig.DEBUG) {
            adView.setAdUnitId(getString(R.string.admob_ad_test_id));
        } else {
            adView.setAdUnitId(getString(R.string.admob_ad_id));
        }
        root.addView(adView, 0);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(root);
        constraintSet.connect(adView.getId(), ConstraintSet.START, root.getId(), ConstraintSet.START);
        constraintSet.connect(adView.getId(), ConstraintSet.END, root.getId(), ConstraintSet.END);
        constraintSet.connect(adView.getId(), ConstraintSet.BOTTOM, root.getId(), ConstraintSet.BOTTOM);

        constraintSet.connect(R.id.rv_statistics ,ConstraintSet.BOTTOM, adView.getId(), ConstraintSet.TOP,0);
        constraintSet.applyTo(root);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
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
