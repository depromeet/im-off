package com.depromeet.tmj.im_off.data.source.local;


import com.depromeet.tmj.im_off.ImOffApplication;
import com.depromeet.tmj.im_off.data.LeavingWork;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LeavingWork.class}, version = 1)
public abstract class ImOffDatabase extends RoomDatabase {
    private static ImOffDatabase INSTANCE;

    public abstract LeavingWorkDao leavingWorkDao();

    private static final Object sLock = new Object();

    public static ImOffDatabase getInstance() {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(ImOffApplication.getApplication(),
                        ImOffDatabase.class, "leaving-work.db").build();
            }
            return INSTANCE;
        }
    }
}
