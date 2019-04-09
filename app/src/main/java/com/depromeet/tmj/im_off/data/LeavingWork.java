package com.depromeet.tmj.im_off.data;

import com.depromeet.tmj.im_off.utils.DateUtils;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "leavingworks")
public class LeavingWork {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String id;

    @NonNull
    @ColumnInfo(name = "leavingtime")
    private Long leavingTime;

    @ColumnInfo(name = "iskaltoe")
    private boolean isKaltoe;

    public LeavingWork(@NonNull Long leavingTime) {
        id = UUID.randomUUID().toString();
        this.leavingTime = leavingTime;

        this.isKaltoe =
                (new Date(leavingTime).after(DateUtils.todayOffStartTime()))
                        && (new Date(leavingTime).before(DateUtils.todayOffEndTime()));

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
}
