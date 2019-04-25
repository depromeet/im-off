package com.depromeet.tmj.im_off.features.statistics;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.shared.RoundProgressBar;
import com.depromeet.tmj.im_off.utils.DisplayUtils;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

public class KaltoeDayViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private TextView tvTitle;
    private TextView tvDay;
    private TextView tvSub;
    private RoundProgressBar progressBar;

    public KaltoeDayViewHolder(@NonNull View view) {
        super(view);
        context = view.getContext();
        tvTitle = view.findViewById(R.id.tv_title);
        tvDay = view.findViewById(R.id.tv_contents_main);
        tvSub = view.findViewById(R.id.tv_contents_sub);
        progressBar = view.findViewById(R.id.progress);
    }

    public void bind(int day) {
        tvTitle.setText("주로 칼퇴하는 요일");
        progressBar.setCricleProgressColor(ContextCompat.getColor(context, R.color.round_blue));
        progressBar.setTextColor(ContextCompat.getColor(context, R.color.white));
        switch (day) {
            case Calendar.MONDAY:
                tvDay.setText("월요일");
                progressBar.setText("Mon");
                break;
            case Calendar.TUESDAY:
                tvDay.setText("화요일");
                progressBar.setText("Tue");
                break;
            case Calendar.WEDNESDAY:
                tvDay.setText("수요일");
                progressBar.setText("Wed");
                break;
            case Calendar.THURSDAY:
                tvDay.setText("목요일");
                progressBar.setText("Thu");
                break;
            case Calendar.FRIDAY:
                tvDay.setText("금요일");
                progressBar.setText("Fri");
                break;
            default:
                tvDay.setText("월요일");
                progressBar.setText("Mon");
                break;
        }

        tvSub.setVisibility(View.INVISIBLE);
        progressBar.setWorkingTimeWithAnim(52);
        progressBar.setTextSize(DisplayUtils.dpToPixel(context, 15));
        progressBar.setTextIsDisplayable(true);
        progressBar.setFont(ResourcesCompat.getFont(context, R.font.spoqa_bold));
    }
}
