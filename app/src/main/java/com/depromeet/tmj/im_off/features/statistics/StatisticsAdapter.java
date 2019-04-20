package com.depromeet.tmj.im_off.features.statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.depromeet.tmj.im_off.R;

import java.util.Date;

public class StatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_WORKING_TIME = 0;
    private static final int TYPE_LEAVING_TIME = 1;
    private static final int TYPE_KALTOE_DAY = 2;
    private static final int TYPE_NIGHT_WORKING_DAY = 3;

    public StatisticsAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_statistics, parent, false);

        switch (viewType) {
            case TYPE_WORKING_TIME:
                return new WorkingTimeViewHolder(view);
            case TYPE_LEAVING_TIME:
                return new LeavingTimeViewHolder(view);
            case TYPE_KALTOE_DAY:
                return new KaltoeDayViewHolder(view);
            case TYPE_NIGHT_WORKING_DAY:
                return new NightWorkingViewHolder(view);
        }
        return new WorkingTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int type) {
        if (holder instanceof WorkingTimeViewHolder) {
            ((WorkingTimeViewHolder) holder).bind(20);
        } else if (holder instanceof LeavingTimeViewHolder) {
            ((LeavingTimeViewHolder) holder).bind(new Date());
        } else if (holder instanceof KaltoeDayViewHolder) {
            ((KaltoeDayViewHolder) holder).bind(1);
        } else if (holder instanceof NightWorkingViewHolder) {
            ((NightWorkingViewHolder) holder).bind(2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_WORKING_TIME;
            case 1:
                return TYPE_LEAVING_TIME;
            case 2:
                return TYPE_KALTOE_DAY;
            case 3:
                return TYPE_NIGHT_WORKING_DAY;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
