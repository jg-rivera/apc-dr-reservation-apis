package apc.entjava.dr_reservation.jsf;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import apc.entjava.dr_reservation.ejb.entity.ReservationEntity;
import apc.entjava.dr_reservation.ejb.repository.IReservationRepository;


@Named
@RequestScoped
public class ReservationFormBean implements Serializable {

    @Inject
    private IReservationRepository reservationRepositoryBean;

    private ReservationEntity currentReservation;

    private List<ReservationEntity> existingReservations;

    private Date today;

    SimpleDateFormat standardTimeFormat;
    SimpleDateFormat militaryTimeFormat;

    public ReservationFormBean() {
        this.today = new Date();
        this.standardTimeFormat = new SimpleDateFormat("hh:mm a");
        this.militaryTimeFormat = new SimpleDateFormat("hh:mm");
        this.currentReservation = new ReservationEntity();
    }

    public void initialize() {
        this.existingReservations = this.reservationRepositoryBean.getItems();
    }

    public void searchByDate() {
        this.existingReservations = this.reservationRepositoryBean.findMatchingReservationsByDate(this.today);
    }

    public String addReservation() {
        if (this.hasScheduleConflict(this.currentReservation)) {
            // show error message
            return "list?faces-redirect=true";
        }

        this.reservationRepositoryBean.addReservation(this.currentReservation);
        return "list?faces-redirect=true";
    }

    public boolean hasScheduleConflict(ReservationEntity newReservation) {
        for (ReservationEntity existingReservation : this.existingReservations) {
            if (existingReservation.getStartTime() >= newReservation.getStartTime() && existingReservation.getEndTime() <= newReservation.getEndTime()) {
                return true;
            }
        }
        return false;
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

    public ReservationEntity getReservation() {
        return currentReservation;
    }

    public void setReservation(ReservationEntity item) {
        this.currentReservation = item;
    }

    public List<ReservationEntity> getExistingReservations() {
        return existingReservations;
    }

    public void setExistingReservations(List<ReservationEntity> existingReservations) {
        this.existingReservations = existingReservations;
    }
}
