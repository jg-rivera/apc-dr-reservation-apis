package apc.entjava.dr_reservation.jsf.helpers;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Singleton
public class TimeIntervalHelper implements ITimeIntervalHelper {

    private SimpleDateFormat standardTimeFormat;
    private SimpleDateFormat militaryTimeFormat;

    public TimeIntervalHelper() {
        this.standardTimeFormat = new SimpleDateFormat("hh:mm a");
        this.militaryTimeFormat = new SimpleDateFormat("hh:mm");
    }

    /**
     * Sets all the time related fields to ZERO!
     *
     * @param date
     *
     * @return Date with hours, minutes, seconds and ms set to ZERO!
     */
    public Date zeroTime( final Date date )
    {
        return this.setTime( date, 0, 0, 0, 0 );
    }

    /**
     * Set the time of the given Date
     *
     * @param date
     * @param hourOfDay
     * @param minute
     * @param second
     * @param ms
     *
     * @return new instance of java.util.Date with the time set
     */
    public Date setTime( final Date date, final int hourOfDay, final int minute, final int second, final int ms )
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( date );
        gc.set( Calendar.HOUR_OF_DAY, hourOfDay );
        gc.set( Calendar.MINUTE, minute );
        gc.set( Calendar.SECOND, second );
        gc.set( Calendar.MILLISECOND, ms );
        return gc.getTime();
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
