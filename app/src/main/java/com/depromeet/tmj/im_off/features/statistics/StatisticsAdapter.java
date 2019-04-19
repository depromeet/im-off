package com.depromeet.tmj.im_off.features.statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.depromeet.tmj.im_off.R;

public class StatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_WORKING_TIME = 1;
    private static final int TYPE_LEAVING_TIME = 2;
    private static final int TYPE_KALTOE_DAY = 3;
    private static final int TYPE_NIGHT_WORKING_DAY = 4;

    public StatisticsAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_statistics, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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
        return TYPE_WORKING_TIME;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
