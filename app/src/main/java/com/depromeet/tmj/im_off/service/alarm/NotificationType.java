package com.depromeet.tmj.im_off.service.alarm;

import com.depromeet.tmj.im_off.R;

public enum NotificationType {
    LEAVING(R.drawable.ic_logo, R.string.noti_title, R.string.noti_message);

    private final int iconRes;
    private final int titleRes;
    private final int messageRes;


    NotificationType(int iconRes, int titleRes, int messageRes) {
        this.iconRes = iconRes;
        this.titleRes = titleRes;
        this.messageRes = messageRes;
    }

    public int getIconRes() {
        return iconRes;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getMessageRes() {
        return messageRes;
    }
}
