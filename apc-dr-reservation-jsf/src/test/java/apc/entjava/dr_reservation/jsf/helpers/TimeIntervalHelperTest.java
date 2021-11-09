package apc.entjava.dr_reservation.jsf.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class TimeIntervalHelperTest {

    TimeIntervalHelper timeIntervalHelper;

    @BeforeEach
    void setUp() {
        timeIntervalHelper = new TimeIntervalHelper();
    }

    @Test
    void testFormatIntervalShouldReturnProperString() throws ParseException {
        String formatted = timeIntervalHelper.formatInterval(7, 30);
        assertEquals("07:30 AM", formatted);
    }

    @Test
    void testFormatIntervalFromMinutesShouldReturnProperString() {
        String formatted = timeIntervalHelper.getFormattedInterval(720);
        assertEquals("07:00 PM", formatted);
    }
}