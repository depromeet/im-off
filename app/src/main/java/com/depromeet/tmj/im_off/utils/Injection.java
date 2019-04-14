package com.depromeet.tmj.im_off.utils;

import com.depromeet.tmj.im_off.data.source.local.ImOffDatabase;
import com.depromeet.tmj.im_off.data.source.local.LeavingWorkLocalDataSource;

public class Injection {
    public static LeavingWorkLocalDataSource provideLeavingWorkRepository() {
        ImOffDatabase database = ImOffDatabase.getInstance();
        return LeavingWorkLocalDataSource.getInstance(new AppExecutors(), database.leavingWorkDao());
    }
}
