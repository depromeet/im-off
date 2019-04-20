package com.depromeet.tmj.im_off.shared;

import com.depromeet.tmj.im_off.R;

public enum DayType {

    SUN(R.string.sun),
    MON(R.string.mon),
    TUE(R.string.tue),
    WED(R.string.wed),
    THU(R.string.thu),
    FRI(R.string.fri),
    SAT(R.string.sat);

    private final int messageRes;

    DayType(int messageRes) {
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

}
