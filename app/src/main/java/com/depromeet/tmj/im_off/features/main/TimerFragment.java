package com.depromeet.tmj.im_off.features.main;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.depromeet.tmj.im_off.BuildConfig;
import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.SettingActivity;
import com.depromeet.tmj.im_off.data.LeavingWork;
import com.depromeet.tmj.im_off.data.source.LeavingWorkDataSource;
import com.depromeet.tmj.im_off.shared.RoundProgressBar;
import com.depromeet.tmj.im_off.utils.DateUtils;
import com.depromeet.tmj.im_off.utils.Injection;
import com.depromeet.tmj.im_off.utils.datastore.AppPreferencesDataStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class TimerFragment extends Fragment {
    private static final String TAG = "TimerFragment";
    public static final int REQUEST_PERMISSIONS = 1;
    private static final String EXTRA_IS_LEAVING = "EXTRA_IS_LEAVING";

    private AppPreferencesDataStore dataStore;

    private ConstraintLayout root;
    private RoundProgressBar roundProgressBar;
    private ImageButton btnSetting;
    private ImageView ivBackgroundCircle;
    private TextView tvStatistics;
    private ScrollCallback scrollCallBack;
    private TextView tvTitle;
    private TextView tvLeavingWork;
    private ImageView btnLeaving;
    private TextView tvLeaving;
    private boolean isLeaving = false;

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
        initArgs();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        initBinding(view);
        initUi();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setCurrentState(DateUtils.nowCalendar());
    }

    private void initArgs() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.isLeaving = bundle.getBoolean(EXTRA_IS_LEAVING, false);
        }
    }

    private void initBinding(View view) {
        root = view.findViewById(R.id.root);
        tvTitle = view.findViewById(R.id.tv_title);
        roundProgressBar = view.findViewById(R.id.round_progress);
        btnSetting = view.findViewById(R.id.btn_setting);
        tvStatistics = view.findViewById(R.id.tv_statistics);
        ivBackgroundCircle = view.findViewById(R.id.iv_bg_circle);
        tvLeavingWork = view.findViewById(R.id.tv_leaving_work_time);
        btnLeaving = view.findViewById(R.id.btn_leaving);
        tvLeaving = view.findViewById(R.id.tv_leaving);
    }

    private void initUi() {
        Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        ivBackgroundCircle.startAnimation(rotation);

        btnSetting.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);
        });

        tvStatistics.setOnClickListener(view -> scrollCallBack.onClickStatistics());
    }

    private void setWeekendUi(Calendar calendar) {
        setTitleText(calendar);
        // 회색 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_gray);

        roundProgressBar.setArcIsDisplayable(false);
        btnLeaving.setVisibility(View.INVISIBLE);
        tvLeavingWork.setVisibility(View.VISIBLE);
    }

    private void setKaltoeResultUi(LeavingWork leavingWork) {
        // title 설정
        tvTitle.setText(getString(R.string.title_kaltoe));

        // 파란 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_blue);

        tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                "오후", dataStore.getLeavingOffHour(), dataStore.getLeavingOffMinute()));

        // 그래프 설정
        SimpleDateFormat subtitleFormat = new SimpleDateFormat("a hh시 mm분 퇴근", Locale.KOREA);
        tvLeavingWork.setText(subtitleFormat.format(leavingWork.getLeavingTime()));

        SimpleDateFormat progressFormat = new SimpleDateFormat("hh:mm", Locale.KOREA);
        roundProgressBar.setText(progressFormat.format(leavingWork.getLeavingTime()));

        SimpleDateFormat ampmFormat = new SimpleDateFormat("a", Locale.US);
        roundProgressBar.setCricleProgressColor(ContextCompat.getColor(getContext(), R.color.round_blue));
        roundProgressBar.setTextAMPM(ampmFormat.format(leavingWork.getLeavingTime()));
        roundProgressBar.setTimeWithAnim(DateUtils.todayStartWorkingTime(Calendar.getInstance()), new Date(leavingWork.getLeavingTime()));

        //버튼 설정
        tvLeaving.setText("공유");
        btnLeaving.setOnClickListener(view -> setShare());
    }

    private void setNightWorkingResultUi(LeavingWork leavingWork) {
        // title 설정
        tvTitle.setText(getString(R.string.title_night_work));

        // 빨간 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_red);

        // 그래프 설정
        SimpleDateFormat subtitleFormat = new SimpleDateFormat("a hh시 mm분 퇴근", Locale.KOREA);
        tvLeavingWork.setText(subtitleFormat.format(leavingWork.getLeavingTime()));

        SimpleDateFormat progressFormat = new SimpleDateFormat("hh:mm", Locale.KOREA);
        roundProgressBar.setText(progressFormat.format(leavingWork.getLeavingTime()));

        SimpleDateFormat ampmFormat = new SimpleDateFormat("a", Locale.US);
        roundProgressBar.setTextAMPM(ampmFormat.format(leavingWork.getLeavingTime()));
        if (getContext() != null) {
            roundProgressBar.setCricleProgressColor(ContextCompat.getColor(getContext(), R.color.round_red));
        }
        roundProgressBar.setTimeWithAnim(DateUtils.todayOffStartTime(), new Date(leavingWork.getLeavingTime()));

        //버튼 설정
        tvLeaving.setText("공유");
        btnLeaving.setOnClickListener(view -> setShare());
    }

    private void setWaitUi(Calendar calendar) {
        setTitleText(calendar);
        // 파란 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_blue);

        if (getContext() != null) {
            roundProgressBar.setCricleProgressColor(ContextCompat.getColor(getContext(), R.color.round_blue));
        }
        roundProgressBar.setText(DateUtils.workingTime(calendar));

        //버튼 설정
        roundProgressBar.setTextAMPMVisible(false);
        tvLeaving.setVisibility(View.INVISIBLE);
        btnLeaving.setVisibility(View.INVISIBLE);
    }

    private void setWorkingUi(Calendar calendar) {
        setTitleText(calendar);

        // 파란 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_blue);

        // 그래프 설정
        if (getContext() != null) {
            roundProgressBar.setCricleProgressColor(ContextCompat.getColor(getContext(), R.color.round_blue));
        }
        roundProgressBar.setTextAMPMVisible(false);
        roundProgressBar.setText(DateUtils.remainingTime(DateUtils.todayOffStartTime(), calendar.getTime()));
        roundProgressBar.setTimeWithAnim(DateUtils.todayStartWorkingTime(calendar), calendar.getTime());

        //버튼 설정
        tvLeaving.setText("퇴근");
        btnLeaving.setOnClickListener(view -> showLeavingDialog());
    }

    private void setNightWorkingUi(Calendar calendar) {
        // title 설정
        tvTitle.setText(getString(R.string.format_night_working));

        // 빨간 눈금 설정
        ivBackgroundCircle.setImageResource(R.drawable.image_dot_circle_red);

        // 퇴근시간 설정
        tvLeavingWork.setText(DateUtils.nightWorkingTimeTitle(calendar));

        // 그래프 설정
        if (getContext() != null) {
            roundProgressBar.setCricleProgressColor(ContextCompat.getColor(getContext(), R.color.round_red));
        }
        roundProgressBar.setTextAMPMVisible(false);
        roundProgressBar.setText(DateUtils.nightWorkingTime(calendar));
        roundProgressBar.setTimeWithAnim(DateUtils.todayOffStartTime(calendar), calendar.getTime());

        //버튼 설정
        tvLeaving.setText("퇴근");
        btnLeaving.setOnClickListener(view -> showLeavingDialog());
    }

    private void setTitleText(Calendar calendar) {
        int hour = dataStore.getLeavingOffHour();

        hour = hour > 12 ? hour - 12 : hour;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                tvTitle.setText(getString(R.string.working_monday));
                // 퇴근시간 설정
                tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                        "오후", hour, dataStore.getLeavingOffMinute()));
                break;
            case Calendar.TUESDAY:
                tvTitle.setText(getString(R.string.working_tuesday));
                // 퇴근시간 설정
                tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                        "오후", hour, dataStore.getLeavingOffMinute()));
                break;
            case Calendar.WEDNESDAY:
                tvTitle.setText(getString(R.string.working_wednsday));
                // 퇴근시간 설정
                tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                        "오후", hour, dataStore.getLeavingOffMinute()));
                break;
            case Calendar.THURSDAY:
                tvTitle.setText(getString(R.string.working_thursday));
                // 퇴근시간 설정
                tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                        "오후", hour, dataStore.getLeavingOffMinute()));
                break;
            case Calendar.FRIDAY:
                tvTitle.setText(getString(R.string.working_friday));
                // 퇴근시간 설정
                tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                        "오후", hour, dataStore.getLeavingOffMinute()));
                break;
            case Calendar.SATURDAY:
                tvTitle.setText(getString(R.string.working_saturday));
                // 퇴근시간 설정
                tvLeavingWork.setText(getString(R.string.leaving_saturday));
                break;
            case Calendar.SUNDAY:
                tvTitle.setText(getString(R.string.working_sunday));
                // 퇴근시간 설정
                tvLeavingWork.setText(getString(R.string.leaving_sunday));
                break;
            default:
                tvTitle.setText(getString(R.string.working_sunday));
                // 퇴근시간 설정
                tvLeavingWork.setText(String.format(getString(R.string.format_leaving_work_time),
                        "오후", dataStore.getLeavingOffHour(), dataStore.getLeavingOffMinute()));
                break;

        }
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
     */
    private void setCurrentState(Calendar calendar) {
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            setWeekendUi(calendar);
        } else {
            Injection.provideLeavingWorkRepository().getLeavingWork(DateUtils.calendar2String(calendar),
                    new LeavingWorkDataSource.GetLeavingWorkCallback() {
                        @Override
                        public void onDataLoaded(LeavingWork leavingWork) {
                            Calendar leavingCal = Calendar.getInstance();
                            leavingCal.setTimeInMillis(leavingWork.getLeavingTime());
                            if (AppPreferencesDataStore.getInstance().getStartWorkingHour() - calendar.get(Calendar.HOUR_OF_DAY) <= 3
                                    && AppPreferencesDataStore.getInstance().getStartWorkingHour() - calendar.get(Calendar.HOUR_OF_DAY) > 0) {
                                setWaitUi(calendar);
                            } else if (leavingWork.isKaltoe()) {
                                setKaltoeResultUi(leavingWork);
                            } else if (leavingCal.get(Calendar.HOUR_OF_DAY) < AppPreferencesDataStore.getInstance().getStartWorkingHour() ||
                                    leavingCal.get(Calendar.HOUR_OF_DAY) >= AppPreferencesDataStore.getInstance().getLeavingOffHour()) {
                                if (leavingWork.isKaltoe()) {
                                    setKaltoeResultUi(leavingWork);
                                } else {
                                    setNightWorkingResultUi(leavingWork);
                                }
                            } else {
                                setWorkingUi(calendar);
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
                                            dataStore.putTodayLeaving(false);
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
                                        if (AppPreferencesDataStore.getInstance().getStartWorkingHour() - calendar.get(Calendar.HOUR_OF_DAY) <= 3
                                                && AppPreferencesDataStore.getInstance().getStartWorkingHour() - calendar.get(Calendar.HOUR_OF_DAY) > 0) {
                                            setWaitUi(calendar);
                                            dataStore.putTodayLeaving(false);
                                        } else {
                                            setNightWorkingUi(calendar);
                                        }
                                    }
                                });
                            } else {
                                // 퇴근시간 이후면 야근중
                                if (calendar.getTime().after(DateUtils.todayOffStartTime())) {
                                    setNightWorkingUi(calendar);
                                } else {
                                    // 아니면 근무중
                                    setWorkingUi(calendar);
                                    dataStore.putTodayLeaving(false);
                                }
                            }
                        }
                    });
        }
    }

    private void showLeavingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.setMessage("퇴근 하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("퇴근", (anInterface, i) -> {
                    Toast.makeText(getContext(), "퇴근!!", Toast.LENGTH_SHORT).show();
                    leaving();
                })
                .setNegativeButton("아직..", (anInterface, i) -> anInterface.dismiss()).show();

        TextView tvTitle = dialog.findViewById(android.R.id.message);
        tvTitle.setTypeface(ResourcesCompat.getFont(getContext(), R.font.jalnan));
        tvTitle.setTextSize(22);

        Button btnPositive = dialog.findViewById(android.R.id.button1);
        Button btnNegative = dialog.findViewById(android.R.id.button2);

        btnPositive.setTextSize(16);
        btnPositive.setTypeface(ResourcesCompat.getFont(getContext(), R.font.spoqa_regular));
        btnPositive.setTextColor(ContextCompat.getColor(getContext(), R.color.round_leaving));
        btnNegative.setTextSize(16);
        btnNegative.setTypeface(ResourcesCompat.getFont(getContext(), R.font.spoqa_regular));
        btnNegative.setTextColor(ContextCompat.getColor(getContext(), R.color.colorText));
    }

    private void leaving() {
        LeavingWork leavingWork = new LeavingWork(DateUtils.nowTime());
        Injection.provideLeavingWorkRepository().saveLeavingWork(leavingWork, () ->
                setCurrentState(DateUtils.nowCalendar()));
        dataStore.putTodayLeaving(true);
    }

    public void setShare() {
        // 권한 확인
        if (!hasWritePermissions() && (getActivity() != null)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    REQUEST_PERMISSIONS);
        } else {
            new Handler().post(() -> {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Uri uri = saveToPicture();
                        if (uri != null) {
                            String message = "나 이때 퇴근함!!";
                            showChooser(message, uri);
                        } else {
                            Toast.makeText(getContext(), "공유 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private boolean hasWritePermissions() {
        if (getContext() != null) {
            int writeExternalPermission =
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return writeExternalPermission != PackageManager.PERMISSION_DENIED;
        }
        throw new RuntimeException("getContext() is null");
    }

    private Uri saveToPicture() {
        if (!isExternalStorageWritable()) {
            Toast.makeText(getContext(), "권한 없음", Toast.LENGTH_SHORT).show();
            return null;
        }
        long currentTime = System.currentTimeMillis();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "im-off");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }

        String mimeType = "image/jpeg";
        String fileName = currentTime + ".jpg";
        String filePath = file.getAbsolutePath() + "/" + fileName;
        Bitmap bitmap = getBitmap();
        FileOutputStream out;
        try {
            out = new FileOutputStream(filePath);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        long size = new File(filePath).length();
        ContentValues values = new ContentValues(8);
        // store the details
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, currentTime);
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.DESCRIPTION, getString(R.string.app_name));
        values.put(MediaStore.Images.Media.ORIENTATION, 0);
        values.put(MediaStore.Images.Media.DATA, filePath);
        values.put(MediaStore.Images.Media.SIZE, size);

        return Objects.requireNonNull(getActivity()).getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private Bitmap getBitmap() {
        Bitmap b = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        root.draw(c);
        return b;
    }

    private void showChooser(String message, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "퇴근 기록 공유"));
        } else {
            Toast.makeText(getContext(), "공유 플랫폼 없음", Toast.LENGTH_SHORT).show();
        }
    }

    public static TimerFragment newInstance(boolean isLeaving) {
        TimerFragment fragment = new TimerFragment();
        Bundle bundle = new Bundle();

        bundle.putBoolean(EXTRA_IS_LEAVING, isLeaving);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface ScrollCallback {
        void onClickStatistics();
    }
}
