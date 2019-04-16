package com.depromeet.tmj.im_off.features.main;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.depromeet.tmj.im_off.BuildConfig;
import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.SettingActivity;
import com.depromeet.tmj.im_off.data.LeavingWork;
import com.depromeet.tmj.im_off.data.source.LeavingWorkDataSource;
import com.depromeet.tmj.im_off.shared.DayType;
import com.depromeet.tmj.im_off.shared.RoundProgressBar;
import com.depromeet.tmj.im_off.utils.DateUtils;
import com.depromeet.tmj.im_off.utils.Injection;
import com.depromeet.tmj.im_off.utils.datastore.AppPreferencesDataStore;

import java.util.Calendar;
import java.util.Date;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class TimerFragment extends Fragment {
    private static final String TAG = "TimerFragment";
    private AppPreferencesDataStore dataStore;

    private RoundProgressBar roundProgressBar;
    private ImageButton btnSetting;
    private ImageView ivBackgroundCircle;
    private TextView tvStatistics;
    private ScrollCallback scrollCallBack;
    private TextView tvTitle;
    private TextView tvLeavingWork;
    private ImageView btnLeaving;

    public TimerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ScrollCallback) {
            scrollCallBack = (ScrollCallback) context;
        } else {
            throw new RuntimeException("ScrollCallback is not implemented");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataStore = AppPreferencesDataStore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        initBinding(view);
        initUi();
        return view;
    }

    private void initBinding(View view) {
        tvTitle = view.findViewById(R.id.tv_title);
        roundProgressBar = view.findViewById(R.id.round_progress);
        btnSetting = view.findViewById(R.id.btn_setting);
        tvStatistics = view.findViewById(R.id.tv_statistics);
        ivBackgroundCircle = view.findViewById(R.id.iv_bg_circle);
        tvLeavingWork = view.findViewById(R.id.tv_leaving_work_time);
        btnLeaving = view.findViewById(R.id.btn_leaving);
    }

    private void initUi() {
        Calendar calendar = DateUtils.nowCalendar();

        if (BuildConfig.DEBUG) {
//            calendar.set(Calendar.DAY_OF_MONTH, 16);
//            calendar.set(Calendar.HOUR_OF_DAY, 7);
        }
        setCurrentState(calendar);

        Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        ivBackgroundCircle.startAnimation(rotation);

        btnSetting.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);
        });

        tvStatistics.setOnClickListener(view -> {
            scrollCallBack.onClickStatistics();
        });

        btnLeaving.setOnClickListener(view -> {
            showLeavingDialog();
        });
    }

    private void setWeekendUi() {
        // title 설정
        tvTitle.setText(String.format(getString(R.string.format_working),
                getString(DayType.values()[DateUtils.getDayOfWeek()].getMessageRes())));

        // 회색 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_gray);

        // 퇴근시간 설정
        tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                "오후", dataStore.getLeavingOffHour(), dataStore.getLeavingOffMinute()));

        roundProgressBar.setArcIsDisplayable(false);
    }

    private void setKaltoeResultUi(LeavingWork leavingWork) {
        // title 설정
        tvTitle.setText(getString(R.string.title_kaltoe));

        // 파란 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_blue);

        // TODO("퇴근시간 설정")
        tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                "오후", dataStore.getLeavingOffHour(), dataStore.getLeavingOffMinute()));

//        roundProgressBar.setText(DateUtils.workingTime(calendar));
        roundProgressBar.setTimeWithAnim(DateUtils.todayStartWorkingTime(Calendar.getInstance()), new Date(leavingWork.getLeavingTime()));
    }

    private void setNightWorkingResultUi(LeavingWork leavingWork) {
        // title 설정
        tvTitle.setText(getString(R.string.title_night_work));

        // 파란 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_red);

        // TODO("퇴근시간 설정")
        tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                "오후", dataStore.getLeavingOffHour(), dataStore.getLeavingOffMinute()));

        roundProgressBar.setCricleProgressColor(ContextCompat.getColor(getContext(), R.color.round_red));
//        roundProgressBar.setText(DateUtils.workingTime(calendar));
        roundProgressBar.setTimeWithAnim(DateUtils.todayOffStartTime(), new Date(leavingWork.getLeavingTime()));
    }

    private void setWaitUi(Calendar calendar) {
        // title 설정
        tvTitle.setText(String.format(getString(R.string.format_working),
                getString(DayType.values()[DateUtils.getDayOfWeek()].getMessageRes())));

        // 파란 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_blue);

        // 퇴근시간 설정
        tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                "오후", dataStore.getLeavingOffHour(), dataStore.getLeavingOffMinute()));

        roundProgressBar.setText(DateUtils.workingTime(calendar));
    }

    private void setWorkingUi(Calendar calendar) {
        // title 설정
        tvTitle.setText(String.format(getString(R.string.format_working),
                getString(DayType.values()[DateUtils.getDayOfWeek()].getMessageRes())));

        // 파란 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_blue);

        // 퇴근시간 설정
        tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                "오후", dataStore.getLeavingOffHour(), dataStore.getLeavingOffMinute()));

        // 그래프 설정
        roundProgressBar.setText(DateUtils.remainingTime(DateUtils.todayOffStartTime(), calendar.getTime()));
        roundProgressBar.setTimeWithAnim(DateUtils.todayStartWorkingTime(calendar), calendar.getTime());
    }

    private void setNightWorkingUi(Calendar calendar) {
        // title 설정
        tvTitle.setText(getString(R.string.format_night_working));

        // 빨간 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_red);

        // 퇴근시간 설정
        tvLeavingWork.setText(DateUtils.nightWorkingTimeTitle());

        // 그래프 설정
        roundProgressBar.setCricleProgressColor(ContextCompat.getColor(getContext(), R.color.round_red));
        roundProgressBar.setText(DateUtils.nightWorkingTime());
        roundProgressBar.setTimeWithAnim(DateUtils.todayOffStartTime(), calendar.getTime());
    }


    /**
     * 오늘 날짜 확인
     * - 주말이면 회색  STATE_WEEKEND
     * - 평일이면 아래로 분기
     * <p>
     * 오늘 퇴근 기록 확인 (평일)
     * - 퇴근기록이 있고 출근시간 3시간 전부터는 오늘 남은 워킹타임 보여주기 STATE_WAIT
     * - 퇴근기록이 있고 출근시간 전이면 출근기록 보여주기 STATE_RESULT
     * - 퇴근기록 없으면 아래로 분기
     * <p>
     * 오늘 시간 확인 (평일, 퇴근기록 없음)
     * - 퇴근시간 안지났으면 파란색 STATE_WORKING
     * - 퇴근시간 지났으면 빨간색 STATE_NIGHT_WORKING
     *
     * @return
     */
    private void setCurrentState(Calendar calendar) {
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            setWeekendUi();
        } else {

            Injection.provideLeavingWorkRepository().getLeavingWork(DateUtils.calendar2String(calendar),
                    new LeavingWorkDataSource.GetLeavingWorkCallback() {
                        @Override
                        public void onDataLoaded(LeavingWork leavingWork) {
                            if (AppPreferencesDataStore.getInstance().getStartWorkingHour() - calendar.get(Calendar.HOUR_OF_DAY) <= 3
                                    && AppPreferencesDataStore.getInstance().getStartWorkingHour() - calendar.get(Calendar.HOUR_OF_DAY) > 0) {
                                setWaitUi(calendar);
                            } else {
                                if (leavingWork.isKaltoe()) {
                                    setKaltoeResultUi(leavingWork);
                                } else {
                                    setNightWorkingResultUi(leavingWork);
                                }
                            }
                        }

                        @Override
                        public void onDataNotAvailable() {
                            if (calendar.getTime().before(DateUtils.todayStartWorkingTime(calendar))) {
                                // 지금 시간이 출근시간 전 -> 어제걸로 다시 조회
                                Injection.provideLeavingWorkRepository().getLeavingWork(DateUtils.yesterday(calendar), new LeavingWorkDataSource.GetLeavingWorkCallback() {
                                    @Override
                                    public void onDataLoaded(LeavingWork leavingWork) {
                                        // 있으나 출근 3시간 전이면 Wait
                                        if (AppPreferencesDataStore.getInstance().getStartWorkingHour() - calendar.get(Calendar.HOUR_OF_DAY) <= 3
                                                && AppPreferencesDataStore.getInstance().getStartWorkingHour() - calendar.get(Calendar.HOUR_OF_DAY) > 0) {
                                            setWaitUi(calendar);
                                        } else {
                                            // 아니면 result
                                            if (leavingWork.isKaltoe()) {
                                                setKaltoeResultUi(leavingWork);
                                            } else {
                                                setNightWorkingResultUi(leavingWork);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onDataNotAvailable() {
                                        // 없으면 야근중
                                        setNightWorkingUi(calendar);
                                    }
                                });
                            } else {
                                // 출근시간 이후면 working
                                setWorkingUi(calendar);
                            }
                        }
                    });
        }
    }

    private void showLeavingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("퇴근")
                .setMessage("퇴근 하나요?")
                .setCancelable(true)
                .setPositiveButton("퇴근", (anInterface, i) -> {
                    Toast.makeText(getContext(), "퇴근!!", Toast.LENGTH_SHORT).show();
                    leaving();
                })
                .setNegativeButton("야근..ㅠ", (anInterface, i) -> {
                    anInterface.dismiss();
                }).show();
    }

    private void leaving() {
        LeavingWork leavingWork = new LeavingWork(DateUtils.nowTime());
        Injection.provideLeavingWorkRepository().saveLeavingWork(leavingWork, () ->
                setCurrentState(DateUtils.nowCalendar()));
    }

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    public interface ScrollCallback {
        void onClickStatistics();
    }
}
