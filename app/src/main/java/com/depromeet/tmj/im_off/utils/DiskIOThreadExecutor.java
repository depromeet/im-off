package com.depromeet.tmj.im_off.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskIOThreadExecutor implements Executor {
    private final Executor diskIO;

    public DiskIOThreadExecutor() {
        diskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(Runnable command) {
        diskIO.execute(command);
    }
}
