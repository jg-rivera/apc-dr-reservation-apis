package apc.entjava.dr_reservation.jsf.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
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
public class ReservationBean implements Serializable {

    @Inject
    private IReservationRepository reservationRepository;

    @Inject
    private IRoomRepository roomRepository;

    @Inject
    private ITimeIntervalHelper timeIntervalHelper;

    private List<ReservationEntity> existingReservations;

    private List<RoomEntity> existingRooms;

    private ReservationEntity currentReservation;

    private Date today;

    private static final int DEFAULT_ROOM = 1;

    public ReservationBean() {
        this.initialDateToday();
        this.currentReservation = new ReservationEntity();
    }

    public void initialDateToday() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        this.today = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public void initializeRooms() {
        this.existingRooms = this.roomRepository.getRooms();
    }

    public void initializeReservations() {
        this.existingReservations = this.reservationRepository.getAllReservationsByDate(this.today);
    }

    public void updateReservationListing(ValueChangeEvent event) {
        String value = event.getNewValue().toString();

        if (value.equalsIgnoreCase("*")) {
            this.existingReservations = this.reservationRepository.getAllReservationsByDate(this.today);
            return;
        }

        int roomId = Integer.valueOf(value);
        this.existingReservations = this.reservationRepository.getAllReservationsByDateAndRoom(today, roomId);
    }

    public String addReservation() {
        if (this.hasSameStartAndEndTime(this.currentReservation)) {
            String summary = "Cannot have the same start and end time!";
            FacesMessage message = new FacesMessage(summary);
            FacesContext.getCurrentInstance().addMessage("form:hasSameStartAndEndTime", message);
            return null;
        }

        if (this.hasGreaterStartTime(this.currentReservation)) {
            String summary = "Start time cannot be greater than the end time!";
            FacesMessage message = new FacesMessage(summary);
            FacesContext.getCurrentInstance().addMessage("form:hasGreaterStartTime", message);
            return null;
        }

        List<ReservationEntity> sameDayAndRoomReservations = this.getSameDayAndRoomReservations(this.currentReservation);
        List<String> scheduleConflicts = this.getScheduleConflicts(this.currentReservation, sameDayAndRoomReservations);

        if (scheduleConflicts.size() > 0) {
            String summary = "Your requested reservation has conflicts with existing reservations:".concat(String.join(",", scheduleConflicts));
            FacesMessage message = new FacesMessage(summary);
            FacesContext.getCurrentInstance().addMessage("form:hasScheduleConflict", message);
            return null;
        }

        int reservationId = this.reservationRepository.addReservation(this.currentReservation);
        this.currentReservation = new ReservationEntity();

        return "list?faces-redirect=true";
    }

    public boolean hasSameStartAndEndTime(ReservationEntity reservation) {
        return reservation.getStartTime() == reservation.getEndTime();
    }

    public boolean hasGreaterStartTime(ReservationEntity reservation) {
        return reservation.getStartTime() > reservation.getEndTime();
    }

    public List<ReservationEntity> getSameDayAndRoomReservations(ReservationEntity reservation) {
        Date currentReservationDate = reservation.getDate();
        int currentRoomId = reservation.getRoom().getId();

        return this.reservationRepository.getAllReservationsByDateAndRoom(currentReservationDate, currentRoomId);
    }

    public List<String> getScheduleConflicts(ReservationEntity newReservation, List<ReservationEntity> sameDayRoomReservations) {
        List<String> scheduleConflicts = new ArrayList<>();

        if (sameDayRoomReservations.isEmpty()) {
            return scheduleConflicts;
        }

        List<ReservationEntity> reservationConflicts = new ArrayList<>();
        
        for (ReservationEntity existingReservation : sameDayRoomReservations) {
            if (existingReservation.conflictsWith(newReservation)) {
                reservationConflicts.add(existingReservation);
            }
        }

        Collections.sort(reservationConflicts);

        for (ReservationEntity reservationConflict : reservationConflicts) {
            int startTime = reservationConflict.getStartTime();
            int endTime = reservationConflict.getEndTime();
            String scheduleConflictTime = this.getFormattedIntervalString(startTime, endTime);
            scheduleConflicts.add(scheduleConflictTime);
        }

        return scheduleConflicts;
    }

    public String getFormattedIntervalString(ReservationEntity entity) {
        return this.getFormattedIntervalString(entity.getStartTime(), entity.getEndTime());
    }

    public String getFormattedIntervalString(int startTime, int endTime) {
        String formattedStartTime = timeIntervalHelper.getFormattedInterval(startTime);
        String formattedEndTime = timeIntervalHelper.getFormattedInterval(endTime);
        return String.format(" %s to %s", formattedStartTime, formattedEndTime);
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

    public String getRoomStyling(RoomEntity room) {
        switch (room.getId()) {
            case 1:
                return "background-color: #513A9F";
            case 2:
                return "background-color: #37579A";
            case 3:
                return "background-color: #171717";
            default:
                return "background-color: #000000";
        }
    }

    public String getRoomShorthand(RoomEntity room) {
        switch (room.getId()) {
            case 1:
                return "DR-A";
            case 2:
                return "DR-B";
            case 3:
                return "DR-C";
            default:
                return "DR";
        }
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
