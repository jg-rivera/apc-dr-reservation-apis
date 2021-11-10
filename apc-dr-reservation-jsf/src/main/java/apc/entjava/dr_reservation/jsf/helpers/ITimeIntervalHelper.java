package apc.entjava.dr_reservation.jsf.helpers;

import java.text.ParseException;
import java.util.Date;

public interface ITimeIntervalHelper {

    String getFormattedInterval(int intervalMinutes);

    String formatInterval(int hours, int minutes) throws ParseException;

    Date zeroTime(final Date date );

    Date setTime( final Date date, final int hourOfDay, final int minute, final int second, final int ms );
}
