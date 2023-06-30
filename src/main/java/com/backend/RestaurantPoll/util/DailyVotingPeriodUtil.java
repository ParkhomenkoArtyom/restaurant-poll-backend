package com.backend.RestaurantPoll.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DailyVotingPeriodUtil {
    //Hour until which the vote is valid
    public static final Integer DAILY_VOTING_END_HOUR = 16;

    //Check if user can vote on restaurant
    public static boolean isVotingValid() {
        return Calendar.getInstance().getTime().before(setDayDetails());
    }

    //Gets day time until which the vote is valid
    private static Date setDayDetails() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, DAILY_VOTING_END_HOUR);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }
}
