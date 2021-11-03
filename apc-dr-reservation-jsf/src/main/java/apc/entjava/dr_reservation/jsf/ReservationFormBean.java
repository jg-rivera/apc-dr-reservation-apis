package apc.entjava.dr_reservation.jsf;

import java.io.Serializable;
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

    public ReservationFormBean() {
        this.currentReservation = new ReservationEntity();
    }

    public void initialize() {
        this.today = new Date();
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

    private boolean hasScheduleConflict(ReservationEntity newReservation) {
        for (ReservationEntity existingReservation : this.existingReservations) {
            if (existingReservation.getStartTime() >= newReservation.getStartTime() && existingReservation.getEndTime() <= newReservation.getEndTime()) {
                return true;
            }
        }
        return false;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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
