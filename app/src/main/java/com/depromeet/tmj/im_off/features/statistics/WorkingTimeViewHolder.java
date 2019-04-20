package com.depromeet.tmj.im_off.features.statistics;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.shared.RoundProgressBar;

public class WorkingTimeViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private TextView tvTitle;
    private TextView tvWorkingtime;
    private TextView tvTotalTime;
    private RoundProgressBar pbWorkingTime;

    public WorkingTimeViewHolder(@NonNull View view) {
        super(view);
        context = view.getContext();
        tvTitle = view.findViewById(R.id.tv_title);
        tvWorkingtime = view.findViewById(R.id.tv_contents_main);
        tvTotalTime = view.findViewById(R.id.tv_contents_sub);
        pbWorkingTime = view.findViewById(R.id.progress);
    }

    public void bind(int workingTime) {
        tvTitle.setText("이번주 근무 시간");
        tvWorkingtime.setText(workingTime + "시간");
        tvTotalTime.setText("/52시간");

        pbWorkingTime.setCricleProgressColor(ContextCompat.getColor(context, R.color.round_blue));
        pbWorkingTime.setWorkingTimeWithAnim(workingTime);
    }
}
