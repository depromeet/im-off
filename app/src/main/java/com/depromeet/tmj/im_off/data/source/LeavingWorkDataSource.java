package com.depromeet.tmj.im_off.data.source;

import com.depromeet.tmj.im_off.data.LeavingWork;

import java.util.List;

import androidx.annotation.NonNull;

public interface LeavingWorkDataSource {

    interface LoadLeavingWorkCallaack {

        void onDataLoaded(List<LeavingWork> leavingWorks);

        void onDataNotAvailable();
    }

    interface GetLeavingWorkCallback {

        void onDataLoaded(LeavingWork leavingWork);

        void onDataNotAvailable();
    }

    interface GetKaltoeCallback {
        void onCountLoaded(int count);
    }

    void getLeavingWorks(@NonNull LoadLeavingWorkCallaack callback);

    void getLeavingWork(@NonNull String id, @NonNull GetLeavingWorkCallback callback);

    void getKaltoeCount(@NonNull GetKaltoeCallback callback);

    void saveLeavingWork(LeavingWork leavingWork);
}
