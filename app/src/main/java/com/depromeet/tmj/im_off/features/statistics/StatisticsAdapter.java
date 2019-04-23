package com.depromeet.tmj.im_off.features.statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.data.LeavingWork;
import com.depromeet.tmj.im_off.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_WORKING_TIME = 0;
    private static final int TYPE_LEAVING_TIME = 1;
    private static final int TYPE_KALTOE_DAY = 2;
    private static final int TYPE_NIGHT_WORKING_DAY = 3;

    private List<LeavingWork> totalLeavingWorks;
    private List<LeavingWork> weekLeavingWorks = new ArrayList<>();

    public StatisticsAdapter(List<LeavingWork> totalLeavingWorks) {
        this.totalLeavingWorks = totalLeavingWorks;

        for (LeavingWork leavingWork : totalLeavingWorks) {
            if (leavingWork.getLeavingTime() > DateUtils.getThisWeekMonday()) {
                weekLeavingWorks.add(leavingWork);
            }
        }
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
            // 최근 1주일 데이터 조회해서 더하기
            ((WorkingTimeViewHolder) holder).bind(getWorkingTime());
        } else if (holder instanceof LeavingTimeViewHolder) {
            // 평균 퇴근 시간
            ((LeavingTimeViewHolder) holder).bind(getAverageLeavingTime());
        } else if (holder instanceof KaltoeDayViewHolder) {
            // 가장 많이 칼퇴하는 요일
            ((KaltoeDayViewHolder) holder).bind(getMostKaltoeDay());
        } else if (holder instanceof NightWorkingViewHolder) {
            // 가장 많이 야근하는 요일
            ((NightWorkingViewHolder) holder).bind(getMostNightWorkDay());
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

    private int getWorkingTime() {
        int workingTime = 0;

        if (weekLeavingWorks != null) {
            for (LeavingWork leavingWork : this.weekLeavingWorks) {
                workingTime += leavingWork.getWorkingTime();
            }
        }

        return workingTime / (1000 * 60 * 60);
    }

    private int getMostKaltoeDay() {
        // 요일별 칼퇴 여부 저장
        List<Integer> kaloteList = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        if (totalLeavingWorks != null) {
            for (LeavingWork leavingWork : this.totalLeavingWorks) {
                if (leavingWork.isKaltoe()) {
                    kaloteList.set(leavingWork.getDayOfWeek(), kaloteList.get(leavingWork.getDayOfWeek()) + 1);
                }
            }
        }

        int max = Collections.max(kaloteList);

        return kaloteList.indexOf(max) + 1;
    }

    private int getMostNightWorkDay() {
        // 요일별 칼퇴 여부 저장
        List<Integer> nightWorkList = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

        if (totalLeavingWorks != null) {
            for (LeavingWork leavingWork : this.totalLeavingWorks) {
                if (!leavingWork.isKaltoe()) {
                    nightWorkList.set(leavingWork.getDayOfWeek(), nightWorkList.get(leavingWork.getDayOfWeek()) + 1);
                }
            }
        }

        int max = Collections.max(nightWorkList);

        return nightWorkList.indexOf(max) + 1;
    }

    private Date getAverageLeavingTime() {
        Long total = 0L;
        // 모든 기록을 동일한 년, 월, 일로 설정 한 뒤, Long 평균 ?
        for(LeavingWork leavingWork: totalLeavingWorks) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(leavingWork.getLeavingTime());
            calendar.set(Calendar.YEAR, DateUtils.nowCalendar().get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, DateUtils.nowCalendar().get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, DateUtils.nowCalendar().get(Calendar.DAY_OF_MONTH));
            total += calendar.getTimeInMillis();
        }

        return new Date(total / totalLeavingWorks.size());
    }
}
