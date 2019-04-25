package com.depromeet.tmj.im_off.features.statistics;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.shared.RoundProgressBar;
import com.depromeet.tmj.im_off.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LeavingTimeViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private TextView tvTitle;
    private TextView tvAvgTime;
    private TextView tvLeavingTime;
    private RoundProgressBar pbLeavingTime;

    public LeavingTimeViewHolder(@NonNull View view) {
        super(view);
        context = view.getContext();
        tvTitle = view.findViewById(R.id.tv_title);
        tvAvgTime = view.findViewById(R.id.tv_contents_main);
        tvLeavingTime = view.findViewById(R.id.tv_contents_sub);
        pbLeavingTime = view.findViewById(R.id.progress);
    }

    public void bind(Date leavingTime) {
        SimpleDateFormat format = new SimpleDateFormat("hh시 mm분", Locale.KOREA);

        tvTitle.setText("평균 퇴근 시간");
        tvAvgTime.setText(format.format(leavingTime));
        tvLeavingTime.setText("/" + format.format(DateUtils.todayOffStartTime()));

        pbLeavingTime.setCricleProgressColor(ContextCompat.getColor(context, R.color.round_leaving));
        pbLeavingTime.setTimeWithAnim(DateUtils.todayStartWorkingTime(DateUtils.nowCalendar()), leavingTime);
    }
}