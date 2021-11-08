package apc.entjava.dr_reservation.jsf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFormBeanTest {

    ReservationFormBean reservationFormBean;

    @BeforeEach
    void setUp() {
        reservationFormBean = new ReservationFormBean();
    }

    @Test
    void testFormatIntervalShouldReturnProperString() throws ParseException {
        String formatted = reservationFormBean.formatInterval(7, 30);
        assertEquals("07:30 AM", formatted);
    }

    @Test
    void testFormatIntervalFromMinutesShouldReturnProperString() {
        String formatted = reservationFormBean.getFormattedInterval(720);
        assertEquals("07:00 PM", formatted);
    }
}