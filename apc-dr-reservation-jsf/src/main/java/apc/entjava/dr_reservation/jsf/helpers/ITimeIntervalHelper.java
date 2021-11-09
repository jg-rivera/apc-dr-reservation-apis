package apc.entjava.dr_reservation.jsf.helpers;

import java.text.ParseException;

public interface ITimeIntervalHelper {

    String getFormattedInterval(int intervalMinutes);

    String formatInterval(int hours, int minutes) throws ParseException;
}
