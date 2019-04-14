package com.depromeet.tmj.im_off;

import com.depromeet.tmj.im_off.utils.DateUtils;
import com.depromeet.tmj.im_off.utils.StateUtils;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void state_isCorrect() {
        Calendar calendar = DateUtils.nowCalendar();

        String state = StateUtils.getCurrentState(calendar);

        assertEquals(StateUtils.STATE_WEEKEND, state);
    }
}