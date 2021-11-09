package apc.entjava.dr_reservation.jsf.helpers;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
public class TimeIntervalHelper implements ITimeIntervalHelper {

    private SimpleDateFormat standardTimeFormat;
    private SimpleDateFormat militaryTimeFormat;

    public TimeIntervalHelper() {
        this.standardTimeFormat = new SimpleDateFormat("hh:mm a");
        this.militaryTimeFormat = new SimpleDateFormat("hh:mm");
    }

    public String getFormattedInterval(int intervalMinutes) {
        final int hours = (intervalMinutes / 60) + 7;
        final int minutes = intervalMinutes % 60;

        try {
            return this.formatInterval(hours, minutes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String formatInterval(int hours, int minutes) throws ParseException {
        final String formattedTime = String.format("%02d:%02d", hours, minutes);

        Date militaryDate = this.militaryTimeFormat.parse(formattedTime);
        return standardTimeFormat.format(militaryDate);
    }
}
