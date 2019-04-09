package com.depromeet.tmj.im_off.data.source.local;

import com.depromeet.tmj.im_off.data.LeavingWork;
import com.depromeet.tmj.im_off.data.source.LeavingWorkDataSource;
import com.depromeet.tmj.im_off.utils.AppExecutors;

import java.util.List;

import androidx.annotation.NonNull;

public class LeavingWorkLocalDataSource implements LeavingWorkDataSource {

    private static LeavingWorkLocalDataSource INSTANCE;

    private LeavingWorkDao leavingWorkDao;

    private AppExecutors appExecutors;

    private LeavingWorkLocalDataSource(@NonNull AppExecutors appExecutors,
                                       @NonNull LeavingWorkDao leavingWorkDao) {
        this.appExecutors = appExecutors;
        this.leavingWorkDao = leavingWorkDao;
    }

    public static LeavingWorkLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                         @NonNull LeavingWorkDao leavingWorkDao) {
        if(INSTANCE == null) {
            synchronized (LeavingWorkLocalDataSource.class) {
                INSTANCE = new LeavingWorkLocalDataSource(appExecutors, leavingWorkDao);
            }
        }
        return INSTANCE;
    }

    @Override
    public void getLeavingWorks(@NonNull final LoadLeavingWorkCallaack callback) {
        Runnable runnable = () -> {
            final List<LeavingWork> leavingWorks = leavingWorkDao.getLeavingWorks();

            appExecutors.getMainThread().execute(() -> {
                if(leavingWorks.isEmpty()) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onDataLoaded(leavingWorks);
                }
            });
        };
        appExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void getLeavingWork(@NonNull final String entryId, @NonNull final GetLeavingWorkCallback callback) {
        Runnable runnable = () -> {
            final LeavingWork leavingWork = leavingWorkDao.getLeavingWorkById(entryId);

            appExecutors.getMainThread().execute(() -> {
                if(leavingWork != null) {
                    callback.onDataLoaded(leavingWork);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };
        appExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void getKaltoeCount(@NonNull final GetKaltoeCallback callback) {
        Runnable runnable = () -> {
            final int kaltoeCount = leavingWorkDao.getKaltoeCount();

            appExecutors.getMainThread().execute(() ->
                    appExecutors.getMainThread().execute(() ->
                            callback.onCountLoaded(kaltoeCount)));
        };
        appExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void saveLeavingWork(LeavingWork leavingWork) {
        Runnable runnable = () -> leavingWorkDao.insertLeavingWork(leavingWork);

        appExecutors.getDiskIO().execute(runnable);
    }
}
