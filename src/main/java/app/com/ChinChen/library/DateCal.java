package app.com.ChinChen.library;

import java.util.Calendar;
import java.util.Date;

public class DateCal {
    public static long dateDiff(Date start_date, Date end_date) {
        try {
            long difference_In_Time
                    = start_date.getTime() - end_date.getTime();

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24));
            return difference_In_Days;
        } catch (Exception e) {
            return 0;
        }
    }

    public static Date DateAddHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
