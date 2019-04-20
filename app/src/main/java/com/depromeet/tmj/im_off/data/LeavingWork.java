package com.depromeet.tmj.im_off.data;

import com.depromeet.tmj.im_off.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "leavingworks")
public class LeavingWork {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private String id;

    @NonNull
    @ColumnInfo(name = "leavingtime")
    private Long leavingTime;

    @ColumnInfo(name = "iskaltoe")
    private boolean isKaltoe;

    @ColumnInfo(name = "wokringtime")
    private Long workingTime;

    public LeavingWork(@NonNull Long leavingTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        this.id = dateFormat.format(new Date(leavingTime));
        this.leavingTime = leavingTime;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(leavingTime);
        this.workingTime = leavingTime - DateUtils.todayStartWorkingTime(calendar).getTime();
        this.isKaltoe = new Date(leavingTime).before(DateUtils.todayOffEndTime());
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public Long getLeavingTime() {
        return leavingTime;
    }

    public boolean isKaltoe() {
        return isKaltoe;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setLeavingTime(@NonNull Long leavingTime) {
        this.leavingTime = leavingTime;
    }

    public void setKaltoe(boolean kaltoe) {
        isKaltoe = kaltoe;
    }

    public void setWorkingTime(Long workingTime) {
        this.workingTime = workingTime;
    }

    public Long getWorkingTime() {
        return workingTime;
    }
}
