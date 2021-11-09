package apc.entjava.dr_reservation.jsf.beans;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import apc.entjava.dr_reservation.ejb.entities.ReservationEntity;
import apc.entjava.dr_reservation.ejb.entities.RoomEntity;
import apc.entjava.dr_reservation.ejb.repositories.IReservationRepository;
import apc.entjava.dr_reservation.ejb.repositories.IRoomRepository;
import apc.entjava.dr_reservation.jsf.helpers.ITimeIntervalHelper;


@Named
@ViewScoped
public class ReservationFormBean implements Serializable {

    @Inject
    private IReservationRepository reservationRepository;

    @Inject
    private IRoomRepository roomRepository;

    @Inject
    private ITimeIntervalHelper timeIntervalHelper;

    private List<ReservationEntity> existingReservations;

    private List<RoomEntity> existingRooms;

    private ReservationEntity currentReservation;


    public ReservationFormBean() {
        this.currentReservation = new ReservationEntity();
    }

    public void initialize() {
        this.existingReservations = this.reservationRepository.getReservations();
        this.existingRooms = this.roomRepository.getRooms();
    }

    public String addReservation() {
        List<String> scheduleConflicts = this.getScheduleConflicts(this.currentReservation, null);

        if (scheduleConflicts.size() > 0) {
            String summary = "Your requested reservation has conflicts with existing reservations: ".concat(String.join(", ", scheduleConflicts));
            FacesMessage message = new FacesMessage(summary);
            FacesContext.getCurrentInstance().addMessage("form:hasScheduleConflict", message);

            return null;
        }

        this.reservationRepository.addReservation(this.currentReservation);
        return "list?faces-redirect=true";
    }

    public List<String> getScheduleConflicts(ReservationEntity newReservation, List<ReservationEntity> reservations) {
        List<String> scheduleConflicts = new ArrayList<>();

        if (this.existingReservations.isEmpty()) {
            return scheduleConflicts;
        }

        for (ReservationEntity existingReservation : reservations) {
            int existingReservationStartTime = existingReservation.getStartTime();
            int existingReservationEndTime = existingReservation.getEndTime();
            int newReservationStartTime = newReservation.getStartTime();
            int newReservationEndTime = newReservation.getEndTime();

            if (existingReservationStartTime >= newReservationStartTime && existingReservationEndTime <= newReservationEndTime) {
                String formattedStartTime = timeIntervalHelper.getFormattedInterval(existingReservationStartTime);
                String formattedEndTime = timeIntervalHelper.getFormattedInterval(existingReservationEndTime);
                String scheduleConflictTime = String.format("%s to %s", formattedStartTime, formattedEndTime);
                scheduleConflicts.add(scheduleConflictTime);
            }
        }

        return scheduleConflicts;
    }

    public Map<Date, List<ReservationEntity>> getReservationsGroupedByDate() {
        return this.existingReservations.stream().collect(Collectors.groupingBy(ReservationEntity::getDate, LinkedHashMap::new, Collectors.toList()));
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

    public List<RoomEntity> getExistingRooms() {
        return existingRooms;
    }

    public void setExistingRooms(List<RoomEntity> existingRooms) {
        this.existingRooms = existingRooms;
    }
}
